package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

public class ActualizarNombreFranquiciaUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public ActualizarNombreFranquiciaUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  inyecto el puerto para no depender de infraestructura
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Void> execute(String franquiciaId, String nuevoNombre) {
        //  busco la franquicia
        //  cambio el nombre
        //  guardo
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    franquicia.cambiarNombre(nuevoNombre);
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save)
                .then();
    }
}
