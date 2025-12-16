package com.accenture.franquicias.infrastructure.persistence.mongo;

import java.util.ArrayList;
import java.util.List;

public class SucursalDocument {

    private String id;

    private String nombre;

    private List<ProductoDocument> productos;

    public SucursalDocument() {
        // dejo este constructor vacío porque Spring Data lo necesita al mapear desde Mongo
        this.productos = new ArrayList<>();
    }

    public SucursalDocument(String id, String nombre, List<ProductoDocument> productos) {
        //  guardo los valores para persistir la sucursal dentro de la franquicia
        this.id = id;
        this.nombre = nombre;
        this.productos = (productos == null) ? new ArrayList<>() : productos;
    }

    public String getId() {
        //  devuelvo el id de la sucursal embebida
        return id;
    }

    public void setId(String id) {
        //  permito set para mapeo automático
        this.id = id;
    }

    public String getNombre() {
        // devuelvo el nombre de la sucursal embebida
        return nombre;
    }

    public void setNombre(String nombre) {
        //  actualizo el nombre en el documento
        this.nombre = nombre;
    }

    public List<ProductoDocument> getProductos() {
        //  devuelvo la lista de productos embebidos
        return productos;
    }

    public void setProductos(List<ProductoDocument> productos) {
        //  evito null para que sea seguro iterar
        this.productos = (productos == null) ? new ArrayList<>() : productos;
    }
}
