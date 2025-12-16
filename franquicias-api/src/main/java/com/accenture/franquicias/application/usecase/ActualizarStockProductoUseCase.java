package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

public class ActualizarStockProductoUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public ActualizarStockProductoUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  recibo el puerto para guardar cambios de forma reactiva
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Void> execute(String franquiciaId, String sucursalId, String productoId, int nuevoStock) {
        //  busco franquicia
        //  busco sucursal
        //  busco producto
        //  cambio stock
        //  guardo y termino
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    Sucursal sucursal = franquicia.obtenerSucursalPorId(sucursalId);
                    Producto producto = sucursal.obtenerProductoPorId(productoId);
                    producto.cambiarStock(nuevoStock);
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save)
                .then();
    }
}
