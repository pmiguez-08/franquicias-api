package com.accenture.franquicias.domain.model;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        // uso esta excepción para indicar que algo no existe en el sistema
        super(message);
    }
}
