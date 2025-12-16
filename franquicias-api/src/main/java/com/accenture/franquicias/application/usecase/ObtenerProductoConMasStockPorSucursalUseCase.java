package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Flux;

public class ObtenerProductoConMasStockPorSucursalUseCase {

    public record ProductoTopStockPorSucursal(String sucursalId, String sucursalNombre, String productoId, String productoNombre, int stock) {
        // uso un record para devolver una vista simple para el endpoint
    }

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public ObtenerProductoConMasStockPorSucursalUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  recibo el puerto para leer la franquicia y transformar el resultado
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Flux<ProductoTopStockPorSucursal> execute(String franquiciaId) {
        //  traigo la franquicia
        //  recorro sus sucursales
        //  saco el producto con más stock por cada sucursal si existe
        //  devuelvo un Flux con la lista resultante
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(reactor.core.publisher.Mono.error(new NotFoundException("franquicia no existe")))
                .flatMapMany(franquicia -> Flux.fromIterable(franquicia.getSucursales()))
                .flatMap(sucursal -> Flux.fromIterable(
                                sucursal.productoConMasStock()
                                        .map(producto -> java.util.List.of(mapear(sucursal, producto)))
                                        .orElseGet(java.util.List::of)
                        )
                );
    }

    private ProductoTopStockPorSucursal mapear(Sucursal sucursal, Producto producto) {
        //  convierto el resultado de dominio a un objeto de respuesta simple
        return new ProductoTopStockPorSucursal(
                sucursal.getId(),
                sucursal.getNombre(),
                producto.getId(),
                producto.getNombre(),
                producto.getStock()
        );
    }
}
