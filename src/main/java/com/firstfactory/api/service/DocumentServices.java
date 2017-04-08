package com.firstfactory.api.service;

import java.io.InputStream;

public interface DocumentServices {

    void createDocument(InputStream file, String fileName, String type, String notes);

    void deleteDocument(String fileName);

    String listAllDocuments();

    void getDocument();
}
