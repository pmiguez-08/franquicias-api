package com.accenture.franquicias.infrastructure.persistence.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "franquicias")
public class FranquiciaDocument {

    @Id
    private String id;

    private String nombre;

    private List<SucursalDocument> sucursales;

    public FranquiciaDocument() {
        //  dejo este constructor vacío porque Spring Data lo necesita al leer desde Mongo
        this.sucursales = new ArrayList<>();
    }

    public FranquiciaDocument(String id, String nombre, List<SucursalDocument> sucursales) {
        //  guardo los valores tal cual porque este objeto es solo para persistir en Mongo
        this.id = id;
        this.nombre = nombre;
        this.sucursales = (sucursales == null) ? new ArrayList<>() : sucursales;
    }

    public String getId() {
        //  devuelvo el id que Mongo usa como clave del documento
        return id;
    }

    public void setId(String id) {
        //  permito set porque Spring Data puede setear campos cuando mapea datos
        this.id = id;
    }

    public String getNombre() {
        //  devuelvo el nombre guardado en Mongo
        return nombre;
    }

    public void setNombre(String nombre) {
        // actualizo el nombre en el documento
        this.nombre = nombre;
    }

    public List<SucursalDocument> getSucursales() {
        //  devuelvo la lista de sucursales embebidas en el documento
        return sucursales;
    }

    public void setSucursales(List<SucursalDocument> sucursales) {
        //  evito null para que nunca explote al recorrer la lista
        this.sucursales = (sucursales == null) ? new ArrayList<>() : sucursales;
    }
}
