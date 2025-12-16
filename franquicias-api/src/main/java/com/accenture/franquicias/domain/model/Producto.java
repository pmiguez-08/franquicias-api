package com.accenture.franquicias.domain.model;

import java.util.Objects;

public class Producto {

    private final String id;
    private String nombre;
    private int stock;

    public Producto(String id, String nombre, int stock) {
        // valido lo básico para no permitir objetos inválidos desde el inicio
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id de producto es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre de producto es obligatorio");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("stock no puede ser negativo");
        }
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
    }

    public String getId() {
        // expongo solo lo necesario para que los casos de uso trabajen
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public void cambiarNombre(String nuevoNombre) {
        // cambio el nombre del producto validando antes para no romper el estado del dominio
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("nuevo nombre de producto es obligatorio");
        }
        this.nombre = nuevoNombre;
    }

    public void cambiarStock(int nuevoStock) {
        // actualizo el stock y evito valores negativos porque eso no tiene sentido en inventario
        if (nuevoStock < 0) {
            throw new IllegalArgumentException("nuevo stock no puede ser negativo");
        }
        this.stock = nuevoStock;
    }

    @Override
    public boolean equals(Object o) {
        // considero dos productos iguales si tienen el mismo id
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Objects.equals(id, producto.id);
    }

    @Override
    public int hashCode() {
        // uso el id para hash porque es la identidad del producto
        return Objects.hash(id);
    }
}
