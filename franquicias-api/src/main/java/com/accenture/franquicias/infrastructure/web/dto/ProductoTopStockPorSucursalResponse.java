package com.accenture.franquicias.infrastructure.web.dto;

public class ProductoTopStockPorSucursalResponse {

    private String sucursalId;
    private String sucursalNombre;
    private String productoId;
    private String productoNombre;
    private int stock;

    public ProductoTopStockPorSucursalResponse() {
        // Constructor vacío para serialización
    }

    public ProductoTopStockPorSucursalResponse(String sucursalId, String sucursalNombre, String productoId, String productoNombre, int stock) {
        // Asigna campos para devolver el producto top por sucursal
        this.sucursalId = sucursalId;
        this.sucursalNombre = sucursalNombre;
        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.stock = stock;
    }

    public String getSucursalId() {
        // Devuelve id de sucursal
        return sucursalId;
    }

    public String getSucursalNombre() {
        // Devuelve nombre de sucursal
        return sucursalNombre;
    }

    public String getProductoId() {
        // Devuelve id de producto
        return productoId;
    }

    public String getProductoNombre() {
        // Devuelve nombre de producto
        return productoNombre;
    }

    public int getStock() {
        // Devuelve stock
        return stock;
    }
}
