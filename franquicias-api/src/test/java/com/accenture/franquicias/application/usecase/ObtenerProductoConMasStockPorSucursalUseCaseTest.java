package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

public class ObtenerProductoConMasStockPorSucursalUseCaseTest {

    private FranquiciaRepositoryPort franquiciaRepositoryPort;
    private ObtenerProductoConMasStockPorSucursalUseCase useCase;

    @BeforeEach
    void setUp() {
        // Se crea un repositorio falso
        franquiciaRepositoryPort = mock(FranquiciaRepositoryPort.class);

        // Se crea el caso de uso con el repositorio falso
        useCase = new ObtenerProductoConMasStockPorSucursalUseCase(franquiciaRepositoryPort);
    }

    @Test
    void deberiaDevolverUnProductoTopPorSucursal() {
        Franquicia franquicia = new Franquicia("F1", "Franquicia 1");

        Sucursal s1 = new Sucursal("S1", "Sucursal 1");
        s1.agregarProducto(new Producto("P1", "A", 10));
        s1.agregarProducto(new Producto("P2", "B", 50));

        Sucursal s2 = new Sucursal("S2", "Sucursal 2");
        s2.agregarProducto(new Producto("P3", "C", 7));
        s2.agregarProducto(new Producto("P4", "D", 9));

        franquicia.agregarSucursal(s1);
        franquicia.agregarSucursal(s2);

        when(franquiciaRepositoryPort.findById("F1")).thenReturn(Mono.just(franquicia));

        StepVerifier.create(useCase.execute("F1"))
                .expectNextMatches(item ->
                        item.sucursalId().equals("S1") &&
                        item.productoId().equals("P2") &&
                        item.stock() == 50
                )
                .expectNextMatches(item ->
                        item.sucursalId().equals("S2") &&
                        item.productoId().equals("P4") &&
                        item.stock() == 9
                )
                .verifyComplete();
    }
}
