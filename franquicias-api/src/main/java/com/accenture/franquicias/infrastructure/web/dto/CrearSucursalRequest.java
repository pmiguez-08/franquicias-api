package com.accenture.franquicias.infrastructure.web.dto;

import jakarta.validation.constraints.NotBlank;

public class CrearSucursalRequest {

    @NotBlank
    private String nombre;

    public CrearSucursalRequest() {
        // Constructor vacío necesario para mapeo desde JSON
    }

    public String getNombre() {
        // Devuelve el nombre de la sucursal enviado
        return nombre;
    }

    public void setNombre(String nombre) {
        // Asigna el nombre de la sucursal enviado
        this.nombre = nombre;
    }
}
