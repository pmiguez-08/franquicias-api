package com.accenture.franquicias.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Franquicia {

    private final String id;
    private String nombre;
    private final List<Sucursal> sucursales;

    public Franquicia(String id, String nombre) {
        // creo una franquicia válida y lista para recibir sucursales
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id de franquicia es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("nombre de franquicia es obligatorio");
        }
        this.id = id;
        this.nombre = nombre;
        this.sucursales = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public void cambiarNombre(String nuevoNombre) {
        // cambio el nombre validando porque el dominio no debe aceptar nombres vacíos
        if (nuevoNombre == null || nuevoNombre.isBlank()) {
            throw new IllegalArgumentException("nuevo nombre de franquicia es obligatorio");
        }
        this.nombre = nuevoNombre;
    }

    public void agregarSucursal(Sucursal sucursal) {
        // evito sucursales duplicadas por id
        if (sucursal == null) {
            throw new IllegalArgumentException("sucursal es obligatoria");
        }
        boolean existe = sucursales.stream().anyMatch(s -> s.getId().equals(sucursal.getId()));
        if (existe) {
            throw new IllegalStateException("ya existe una sucursal con ese id en la franquicia");
        }
        sucursales.add(sucursal);
    }

    public Sucursal obtenerSucursalPorId(String sucursalId) {
        // busco la sucursal y si no existe fallo rápido para que el caso de uso lo maneje
        Optional<Sucursal> encontrada = sucursales.stream().filter(s -> s.getId().equals(sucursalId)).findFirst();
        return encontrada.orElseThrow(() -> new IllegalStateException("sucursal no existe en la franquicia"));
    }

    @Override
    public boolean equals(Object o) {
        // considero dos franquicias iguales si tienen el mismo id
        if (this == o) return true;
        if (!(o instanceof Franquicia franquicia)) return false;
        return Objects.equals(id, franquicia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
