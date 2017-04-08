package com.firstfactory.api.service;

import com.firstfactory.api.entity.Document;
import com.firstfactory.api.entity.DocumentList;
import com.firstfactory.api.exception.DocumentHandlerException;
import com.firstfactory.api.storage.DocumentStorage;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@NoArgsConstructor
public class DefaultDocumentServices implements DocumentServices {

    private static final String DEFAULT_DIR = "/doc-repository/";
    private static final String DOCUMENT_TABLE = "repository";

    @Override
    public void createDocument(InputStream file, String fileName, String type, String notes) {
        try {
            new DocumentStorage().insertRecord(DOCUMENT_TABLE, Document.castToMap(new Document(fileName, type, notes)));
            this.storeFile(file, fileName);
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteDocument(String fileName) {
        try {
            this.deleteFile(fileName);
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public DocumentList listAllDocuments() {
        try {
            this.listAllFiles();
            return new DocumentList(new DocumentStorage().readAll(DOCUMENT_TABLE)
                    .stream().map(Document::castFromMap).collect(Collectors.toList())
            );
        } catch (IOException e) {
            throw new DocumentHandlerException(e.getMessage(), e);
        }
    }

    @Override
    public void getDocument() {
        throw new UnsupportedOperationException();
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

    private String listAllFiles() throws IOException {
        return Files.list(Paths.get(DEFAULT_DIR)).filter(Files::isRegularFile)
                .map(f -> f.toFile().getName()).collect(Collectors.joining(", "));
    }

    private void deleteFile(String fileName) throws IOException {
        Files.deleteIfExists(Paths.get(DefaultDocumentServices.fullPath(fileName)));
    }

    private static String fullPath(String fileName) {
        return DEFAULT_DIR + fileName;
    }
}
