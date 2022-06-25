package com.coderbank.cliente.controller.exceptions;

public class ObjectNotFoundException extends RuntimeException{
    private static final long seriaVersionUID = 1L;

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
