package com.accenture.franquicias.infrastructure.web.dto;

import java.util.List;

public class SucursalResponse {

    private String id;
    private String nombre;
    private List<ProductoResponse> productos;

    public SucursalResponse() {
        // Constructor vacío para serialización
    }

    public SucursalResponse(String id, String nombre, List<ProductoResponse> productos) {
        // Asigna campos para devolver al cliente
        this.id = id;
        this.nombre = nombre;
        this.productos = productos;
    }

    public String getId() {
        // Devuelve id de sucursal
        return id;
    }

    public String getNombre() {
        // Devuelve nombre de sucursal
        return nombre;
    }

    public List<ProductoResponse> getProductos() {
        // Devuelve lista de productos
        return productos;
    }
}
