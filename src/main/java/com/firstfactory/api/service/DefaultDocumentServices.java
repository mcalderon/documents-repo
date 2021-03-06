package com.firstfactory.api.service;

import com.firstfactory.api.entity.Document;
import com.firstfactory.api.entity.DocumentList;
import com.firstfactory.api.exception.DocumentHandlerException;
import com.firstfactory.api.storage.DefaultDocumentStorageImpl;
import com.firstfactory.api.storage.DocumentStorage;

import javax.ws.rs.core.StreamingOutput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class DefaultDocumentServices implements DocumentServices {

    private DocumentStorage documentStorage;
    private static final String DEFAULT_DIR = "/doc-repository/";
    private static final String DOCUMENT_TABLE = "repository";

    public DefaultDocumentServices() {
        documentStorage = new DefaultDocumentStorageImpl();
    }

    @Override
    public void createDocument(InputStream file, String fileName, String type, String notes) {
        try {
            this.documentStorage.insertRecord(DOCUMENT_TABLE, Document.castToMap(new Document(fileName, type, notes)));
            this.storeFile(file, fileName);
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteDocument(int id) {
        try {
            final Document documentToDelete = this.getDocument(id);
            if (this.documentStorage.deleteRecord(DOCUMENT_TABLE, id) != 0) {
                this.deleteFile(documentToDelete.getFileText());
            }
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentList listAllDocuments() {
        return new DocumentList(this.documentStorage.findAll(DOCUMENT_TABLE)
                .stream().map(Document::castFromMap).collect(Collectors.toList())
        );
    }

    @Override
    public Document getDocument(int id) {
        return Document.castFromMap(this.documentStorage.findById(DOCUMENT_TABLE, id));
    }

    @Override
    public StreamingOutput downloadDocument(String fileName) {
        if (!Paths.get(DefaultDocumentServices.fullPath(fileName)).toFile().exists()) {
            throw new DocumentHandlerException("File does not exist in the repository");
        }
        return outputStream -> {
            try {
                Path path = Paths.get(fullPath(fileName));
                byte[] data = Files.readAllBytes(path);
                outputStream.write(data);
                outputStream.flush();
            } catch (IOException e) {
                throw new DocumentHandlerException(e.getMessage(), e);
            }
        };
    }

    private void storeFile(InputStream file, String fileName) throws IOException {
        int read;
        byte[] bytes = new byte[1024];
        try (OutputStream stream = new FileOutputStream(new File(DefaultDocumentServices.fullPath(fileName)))) {
            while ((read = file.read(bytes)) != -1) {
                stream.write(bytes, 0, read);
            }
        }
    }

    private void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(DefaultDocumentServices.fullPath(fileName)));
    }

    private static String fullPath(String fileName) {
        return DEFAULT_DIR + fileName;
    }
}
