package com.mikaelfrancoeur.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(Class<?> clazz, Long id) {
        super(String.format("Could not find instance of class %s with id %d", clazz.getName(), id));
    }
}
