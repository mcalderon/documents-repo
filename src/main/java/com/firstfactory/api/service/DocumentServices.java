package com.firstfactory.api.service;

import com.firstfactory.api.entity.Document;
import com.firstfactory.api.entity.DocumentList;

import java.io.InputStream;

public interface DocumentServices {

    void createDocument(InputStream file, String fileName, String type, String notes);

    void deleteDocument(String fileName);

    DocumentList listAllDocuments();

    Document getDocument(int id);
}
