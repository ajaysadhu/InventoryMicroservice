package com.neonq.inventory.exception;

public class ResourceExistsWarning extends RuntimeException {

    public ResourceExistsWarning() {
        super();
    }

    public ResourceExistsWarning(final String message) {
        super(message);
    }
}
