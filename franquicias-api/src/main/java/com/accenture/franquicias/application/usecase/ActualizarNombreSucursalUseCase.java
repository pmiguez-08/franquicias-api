package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

public class ActualizarNombreSucursalUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public ActualizarNombreSucursalUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  uso el puerto para leer y guardar de forma reactiva
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Void> execute(String franquiciaId, String sucursalId, String nuevoNombre) {
        //  busco franquicia
        //  busco sucursal
        //  cambio nombre
        //  guardo
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    Sucursal sucursal = franquicia.obtenerSucursalPorId(sucursalId);
                    sucursal.cambiarNombre(nuevoNombre);
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save)
                .then();
    }
}
