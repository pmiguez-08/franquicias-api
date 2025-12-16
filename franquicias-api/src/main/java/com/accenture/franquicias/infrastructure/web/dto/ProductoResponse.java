package com.accenture.franquicias.infrastructure.web.dto;

public class ProductoResponse {

    private String id;
    private String nombre;
    private int stock;

    public ProductoResponse() {
        // Constructor vacío para serialización
    }

    public ProductoResponse(String id, String nombre, int stock) {
        // Asigna campos para devolver al cliente
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getId() {
        // Devuelve id del producto
        return id;
    }

    public String getNombre() {
        // Devuelve nombre del producto
        return nombre;
    }

    public int getStock() {
        // Devuelve stock del producto
        return stock;
    }
}
