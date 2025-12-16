package com.accenture.franquicias.infrastructure.web.mapper;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.infrastructure.web.dto.FranquiciaResponse;
import com.accenture.franquicias.infrastructure.web.dto.ProductoResponse;
import com.accenture.franquicias.infrastructure.web.dto.SucursalResponse;

import java.util.List;
import java.util.stream.Collectors;

public class FranquiciaWebMapper {

    public static FranquiciaResponse toResponse(Franquicia franquicia) {
        // Convierte franquicia de dominio a un JSON bonito para devolver al cliente
        List<SucursalResponse> sucursales = franquicia.getSucursales()
                .stream()
                .map(FranquiciaWebMapper::toResponse)
                .collect(Collectors.toList());

        return new FranquiciaResponse(franquicia.getId(), franquicia.getNombre(), sucursales);
    }

    private static SucursalResponse toResponse(Sucursal sucursal) {
        // Convierte sucursal de dominio a respuesta
        List<ProductoResponse> productos = sucursal.getProductos()
                .stream()
                .map(FranquiciaWebMapper::toResponse)
                .collect(Collectors.toList());

        return new SucursalResponse(sucursal.getId(), sucursal.getNombre(), productos);
    }

    private static ProductoResponse toResponse(Producto producto) {
        // Convierte producto de dominio a respuesta
        return new ProductoResponse(producto.getId(), producto.getNombre(), producto.getStock());
    }
}
