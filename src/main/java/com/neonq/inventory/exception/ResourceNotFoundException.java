package com.neonq.inventory.exception;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 5201532697764373586L;

    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
