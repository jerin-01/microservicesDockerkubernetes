package com.jerin.accounts.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s Requested Data Not Found for %s %s", resourceName,fieldName,fieldValue));
    }
}
