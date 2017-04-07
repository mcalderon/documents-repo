package com.firstfactory.api.service;

import com.firstfactory.api.exception.DocumentHandlerException;

import java.io.InputStream;

public interface DocumentServices {

    void createDocument(InputStream file, String fileName, String type, String notes) throws DocumentHandlerException;

    void deleteDocument(String fileName) throws DocumentHandlerException;

    String listAllDocuments() throws DocumentHandlerException;

    void getDocument();
}
