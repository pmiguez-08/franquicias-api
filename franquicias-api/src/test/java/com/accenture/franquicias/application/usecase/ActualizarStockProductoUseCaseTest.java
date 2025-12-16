package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ActualizarStockProductoUseCaseTest {

    private FranquiciaRepositoryPort franquiciaRepositoryPort;
    private ActualizarStockProductoUseCase actualizarStockProductoUseCase;

    @BeforeEach
    void setUp() {
        // Se crea un repositorio falso
        franquiciaRepositoryPort = mock(FranquiciaRepositoryPort.class);

        // Se crea el caso de uso con el repositorio falso
        actualizarStockProductoUseCase = new ActualizarStockProductoUseCase(franquiciaRepositoryPort);
    }

    @Test
    void deberiaActualizarStockYGuardar() {
        Franquicia franquicia = new Franquicia("F1", "Franquicia 1");
        Sucursal sucursal = new Sucursal("S1", "Sucursal 1");
        Producto producto = new Producto("P1", "Producto 1", 10);

        sucursal.agregarProducto(producto);
        franquicia.agregarSucursal(sucursal);

        when(franquiciaRepositoryPort.findById("F1")).thenReturn(Mono.just(franquicia));
        when(franquiciaRepositoryPort.save(any(Franquicia.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        StepVerifier.create(actualizarStockProductoUseCase.execute("F1", "S1", "P1", 120))
                .verifyComplete();

        // Se valida que el stock cambió en el modelo de dominio
        if (producto.getStock() != 120) {
            throw new AssertionError("stock no se actualizó");
        }

        verify(franquiciaRepositoryPort, times(1)).findById("F1");
        verify(franquiciaRepositoryPort, times(1)).save(any(Franquicia.class));
    }
}
