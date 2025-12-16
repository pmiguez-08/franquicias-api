package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class CrearFranquiciaUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public CrearFranquiciaUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        // inyecto el puerto para no depender de detalles como Mongo o Spring
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Franquicia> execute(String nombreFranquicia) {
        // creo un id y construyo el modelo del dominio
        // devuelvo Mono porque todo el flujo es reactivo
        return Mono.fromSupplier(() -> {
                    String id = UUID.randomUUID().toString();
                    return new Franquicia(id, nombreFranquicia);
                })
                .flatMap(franquiciaRepositoryPort::save);
    }
}
