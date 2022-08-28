package com.neonq.inventory.exception;

public class StockUnavailableException extends RuntimeException{
    public StockUnavailableException() {
        super();
    }

    public StockUnavailableException(final String message) {
        super(message);
    }
}
