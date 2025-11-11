package com.mohsenko.e_commerce_mohsenko.exception;

import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseException {

    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue),
              HttpStatus.CONFLICT);
    }
}
