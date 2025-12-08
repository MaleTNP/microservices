package com.thanapongBank.cards.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String fieldName, String fieldValue) {
        super(String.format("%s not found %s : '%s'", resource, fieldName, fieldValue));
    }
}
