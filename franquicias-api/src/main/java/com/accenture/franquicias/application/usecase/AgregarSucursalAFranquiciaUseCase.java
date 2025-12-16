package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class AgregarSucursalAFranquiciaUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public AgregarSucursalAFranquiciaUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        // recibo el repositorio como puerto para mantener Clean Architecture
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Franquicia> execute(String franquiciaId, String nombreSucursal) {
        // busco la franquicia primero
        //  agrego la sucursal en memoria usando reglas de dominio
        //  guardo la franquicia modificada
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    String sucursalId = UUID.randomUUID().toString();
                    franquicia.agregarSucursal(new Sucursal(sucursalId, nombreSucursal));
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save);
    }
}
