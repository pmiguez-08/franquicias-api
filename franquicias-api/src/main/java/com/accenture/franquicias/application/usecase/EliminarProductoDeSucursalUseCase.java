
package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

public class EliminarProductoDeSucursalUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public EliminarProductoDeSucursalUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  uso el puerto para mantener independencia de infraestructura
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Void> execute(String franquiciaId, String sucursalId, String productoId) {
        //  elimino el producto y guardo la franquicia
        //  devuelvo Mono Void porque el endpoint de borrar no necesita cuerpo de respuesta
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    Sucursal sucursal = franquicia.obtenerSucursalPorId(sucursalId);
                    sucursal.eliminarProducto(productoId);
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save)
                .then();
    }
}
