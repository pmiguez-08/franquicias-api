package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CrearFranquiciaUseCaseTest {

    private FranquiciaRepositoryPort franquiciaRepositoryPort;
    private CrearFranquiciaUseCase crearFranquiciaUseCase;

    @BeforeEach
    void setUp() {
        // Se crea un repositorio falso para no usar Mongo real en un unit test
        franquiciaRepositoryPort = mock(FranquiciaRepositoryPort.class);

        // Se crea el caso de uso con el repositorio falso
        crearFranquiciaUseCase = new CrearFranquiciaUseCase(franquiciaRepositoryPort);
    }

    @Test
    void deberiaCrearYGuardarFranquicia() {
        // Se define que al guardar se devuelve la misma franquicia recibida
        when(franquiciaRepositoryPort.save(any(Franquicia.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<Franquicia> resultado = crearFranquiciaUseCase.execute("Franquicia Prueba");

        StepVerifier.create(resultado)
                .assertNext(franquicia -> {
                    // Se valida que el id exista y no esté vacío
                    if (franquicia.getId() == null || franquicia.getId().isBlank()) {
                        throw new AssertionError("id no fue generado");
                    }

                    // Se valida que el nombre sea el mismo enviado
                    if (!"Franquicia Prueba".equals(franquicia.getNombre())) {
                        throw new AssertionError("nombre no coincide");
                    }
                })
                .verifyComplete();

        // Se valida que save se haya llamado una vez
        verify(franquiciaRepositoryPort, times(1)).save(any(Franquicia.class));
    }
}
