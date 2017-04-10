package com.firstfactory.api.exception;

public class DocumentHandlerException extends RuntimeException {

    public DocumentHandlerException(String message) {
        super(message);
    }

    public DocumentHandlerException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
