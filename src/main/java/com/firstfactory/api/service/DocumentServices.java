package com.firstfactory.api.service;

import com.firstfactory.api.entity.Document;
import com.firstfactory.api.entity.DocumentList;

import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;

public interface DocumentServices {

    void createDocument(InputStream file, String fileName, String type, String notes);

    void deleteDocument(int id);

    DocumentList listAllDocuments();

    Document getDocument(int id);

    StreamingOutput downloadDocument(String fileName);
}
