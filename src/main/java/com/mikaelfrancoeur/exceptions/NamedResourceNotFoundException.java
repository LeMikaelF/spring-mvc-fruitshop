package com.mikaelfrancoeur.exceptions;

public class NamedResourceNotFoundException extends RuntimeException {
    public NamedResourceNotFoundException(Class<?> clazz, Long id) {
        super(String.format("Could not find instance of class %s with id %d", clazz.getName(), id));
    }
}
