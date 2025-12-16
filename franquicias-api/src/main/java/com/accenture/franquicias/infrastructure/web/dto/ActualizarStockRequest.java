package com.accenture.franquicias.infrastructure.web.dto;

import jakarta.validation.constraints.Min;

public class ActualizarStockRequest {

    @Min(0)
    private int stock;

    public ActualizarStockRequest() {
        // Constructor vacío necesario para mapeo desde JSON
    }

    public int getStock() {
        // Devuelve el stock nuevo enviado
        return stock;
    }

    public void setStock(int stock) {
        // Asigna el stock nuevo enviado
        this.stock = stock;
    }
}
