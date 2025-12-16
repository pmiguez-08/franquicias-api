package com.accenture.franquicias.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Sucursal {

    private final String id;
    private String nombre;
    private final List<Producto> productos;

    public Sucursal(String id, String nombre) {
        // inicializo la sucursal con valores válidos
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id de sucursal es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre de sucursal es obligatorio");
        }
        this.id = id;
        this.nombre = nombre;
        this.productos = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Producto> getProductos() {
        // devuelvo la lista para lectura y manejo la mutación dentro de métodos de dominio
        return productos;
    }

    public void cambiarNombre(String nuevoNombre) {
        // cambio el nombre de la sucursal validando antes
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("nuevo nombre de sucursal es obligatorio");
        }
        this.nombre = nuevoNombre;
    }

    public void agregarProducto(Producto producto) {
        // evito productos duplicados por id dentro de la misma sucursal
        if (producto == null) {
            throw new IllegalArgumentException("producto es obligatorio");
        }
        boolean existe = productos.stream().anyMatch(p -> p.getId().equals(producto.getId()));
        if (existe) {
            throw new IllegalStateException("ya existe un producto con ese id en la sucursal");
        }
        productos.add(producto);
    }

    public void eliminarProducto(String productoId) {
        // elimino por id y si no existe lanzo error para que el caso de uso lo comunique bien
        boolean removido = productos.removeIf(p -> p.getId().equals(productoId));
        if (!removido) {
            throw new IllegalStateException("producto no existe en la sucursal");
        }
    }

    public Producto obtenerProductoPorId(String productoId) {
        // busco el producto y si no existe fallo rápido para mantener consistencia
        Optional<Producto> encontrado = productos.stream().filter(p -> p.getId().equals(productoId)).findFirst();
        return encontrado.orElseThrow(() -> new IllegalStateException("producto no existe en la sucursal"));
    }

    public Optional<Producto> productoConMasStock() {
        // calculo el producto con más stock en esta sucursal
        return productos.stream().max((a, b) -> Integer.compare(a.getStock(), b.getStock()));
    }

    @Override
    public boolean equals(Object o) {
        // considero dos sucursales iguales si tienen el mismo id
        if (this == o) return true;
        if (!(o instanceof Sucursal sucursal)) return false;
        return Objects.equals(id, sucursal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
