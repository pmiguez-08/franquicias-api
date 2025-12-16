package com.accenture.franquicias.infrastructure.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class CrearProductoRequest {

    @NotBlank
    private String nombre;

    @Min(0)
    private int stock;

    public CrearProductoRequest() {
        // Constructor vacío necesario para mapeo desde JSON
    }

    public String getNombre() {
        // Devuelve el nombre del producto enviado
        return nombre;
    }

    public void setNombre(String nombre) {
        // Asigna el nombre del producto enviado
        this.nombre = nombre;
    }

    public int getStock() {
        // Devuelve el stock enviado
        return stock;
    }

    public void setStock(int stock) {
        // Asigna el stock enviado
        this.stock = stock;
    }
}
