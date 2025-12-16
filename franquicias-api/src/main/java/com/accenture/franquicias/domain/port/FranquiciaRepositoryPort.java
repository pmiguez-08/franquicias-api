package com.accenture.franquicias.domain.port;

import com.accenture.franquicias.domain.model.Franquicia;
import reactor.core.publisher.Mono;

// Puerto del dominio.
// Define lo que el negocio necesita para persistir franquicias
public interface FranquiciaRepositoryPort {

    // Guarda una franquicia completa y devuelve el resultado
    Mono<Franquicia> save(Franquicia franquicia);

    // Busca una franquicia por su identificador    
    Mono<Franquicia> findById(String franquiciaId);
}
