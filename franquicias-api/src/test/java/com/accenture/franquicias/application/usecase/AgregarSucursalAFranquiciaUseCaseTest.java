package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AgregarSucursalAFranquiciaUseCaseTest {

    private FranquiciaRepositoryPort franquiciaRepositoryPort;
    private AgregarSucursalAFranquiciaUseCase agregarSucursalAFranquiciaUseCase;

    @BeforeEach
    void setUp() {
        // Se crea un repositorio falso
        franquiciaRepositoryPort = mock(FranquiciaRepositoryPort.class);

        // Se crea el caso de uso con el repositorio falso
        agregarSucursalAFranquiciaUseCase = new AgregarSucursalAFranquiciaUseCase(franquiciaRepositoryPort);
    }

    @Test
    void deberiaAgregarSucursalYGuardar() {
        Franquicia franquicia = new Franquicia("F1", "Franquicia 1");

        // Se simula que findById encuentra la franquicia
        when(franquiciaRepositoryPort.findById("F1")).thenReturn(Mono.just(franquicia));

        // Se simula que save devuelve el objeto guardado
        when(franquiciaRepositoryPort.save(any(Franquicia.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(agregarSucursalAFranquiciaUseCase.execute("F1", "Sucursal Centro"))
                .assertNext(actualizada -> {
                    // Se valida que exista una sucursal agregada
                    if (actualizada.getSucursales().size() != 1) {
                        throw new AssertionError("no se agregó la sucursal");
                    }

                    // Se valida que el nombre de la sucursal sea el esperado
                    if (!"Sucursal Centro".equals(actualizada.getSucursales().get(0).getNombre())) {
                        throw new AssertionError("nombre de sucursal no coincide");
                    }
                })
                .verifyComplete();

        verify(franquiciaRepositoryPort, times(1)).findById("F1");
        verify(franquiciaRepositoryPort, times(1)).save(any(Franquicia.class));
    }

    @Test
    void deberiaFallarSiNoExisteFranquicia() {
        // Se simula que findById no encuentra nada
        when(franquiciaRepositoryPort.findById("F404")).thenReturn(Mono.empty());

        StepVerifier.create(agregarSucursalAFranquiciaUseCase.execute("F404", "Sucursal X"))
                .expectError(NotFoundException.class)
                .verify();

        verify(franquiciaRepositoryPort, times(1)).findById("F404");
        verify(franquiciaRepositoryPort, never()).save(any());
    }
}
