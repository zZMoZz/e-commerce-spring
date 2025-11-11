package com.mohsenko.e_commerce_mohsenko.exception;

import org.springframework.http.HttpStatus;

public class DatabaseException extends BaseException {
    public DatabaseException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
    }
}
