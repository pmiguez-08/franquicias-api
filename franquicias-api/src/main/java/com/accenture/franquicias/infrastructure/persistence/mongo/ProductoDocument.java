package com.accenture.franquicias.infrastructure.persistence.mongo;

public class ProductoDocument {

    private String id;

    private String nombre;

    private int stock;

    public ProductoDocument() {
        // dejo este constructor vacío porque Spring Data lo necesita al leer desde Mongo
    }

    public ProductoDocument(String id, String nombre, int stock) {
        // guardo los valores del producto para persistirlos
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getId() {
        //  devuelvo el id del producto embebido
        return id;
    }

    public void setId(String id) {
        //  permito set para mapeo automático
        this.id = id;
    }

    public String getNombre() {
        // devuelvo el nombre del producto
        return nombre;
    }

    public void setNombre(String nombre) {
        //  actualizo el nombre del producto en el documento
        this.nombre = nombre;
    }

    public int getStock() {
        //  devuelvo el stock del producto
        return stock;
    }

    public void setStock(int stock) {
        //  actualizo el stock del producto en el documento
        this.stock = stock;
    }
}
