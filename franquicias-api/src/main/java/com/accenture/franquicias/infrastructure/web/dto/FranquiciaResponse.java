package com.accenture.franquicias.infrastructure.web.dto;

import java.util.List;

public class FranquiciaResponse {

    private String id;
    private String nombre;
    private List<SucursalResponse> sucursales;

    public FranquiciaResponse() {
        // Constructor vacío para serialización
    }

    public FranquiciaResponse(String id, String nombre, List<SucursalResponse> sucursales) {
        // Asigna campos para devolver al cliente
        this.id = id;
        this.nombre = nombre;
        this.sucursales = sucursales;
    }

    public String getId() {
        // Devuelve id de franquicia
        return id;
    }

    public String getNombre() {
        // Devuelve nombre de franquicia
        return nombre;
    }

    public List<SucursalResponse> getSucursales() {
        // Devuelve lista de sucursales
        return sucursales;
    }
}
