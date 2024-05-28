package com.om.application.product.exception.userdefined;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String productNotFound) {
    }
}
