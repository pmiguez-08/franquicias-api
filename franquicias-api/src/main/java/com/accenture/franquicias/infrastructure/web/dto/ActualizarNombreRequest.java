package com.accenture.franquicias.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public class ActualizarNombreRequest {

    @NotBlank
    private String nombre;

    public ActualizarNombreRequest() {
        // Constructor vacío necesario para que Spring convierta JSON a este objeto
    }

    public String getNombre() {
        // Devuelve el nombre nuevo enviado por el cliente
        return nombre;
    }

    public void setNombre(String nombre) {
        // Asigna el nombre nuevo enviado por el cliente
        this.nombre = nombre;
    }
}
