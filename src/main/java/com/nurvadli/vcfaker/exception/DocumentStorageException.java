package com.nurvadli.vcfaker.exception;

public class DocumentStorageException extends RuntimeException {

    public DocumentStorageException() {
    }

    public DocumentStorageException(String message) {
        super(message);
    }

    public DocumentStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
