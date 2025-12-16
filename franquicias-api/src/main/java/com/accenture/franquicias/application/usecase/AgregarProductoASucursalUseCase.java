package com.accenture.franquicias.application.usecase;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.NotFoundException;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class AgregarProductoASucursalUseCase {

    private final FranquiciaRepositoryPort franquiciaRepositoryPort;

    public AgregarProductoASucursalUseCase(FranquiciaRepositoryPort franquiciaRepositoryPort) {
        //  dependo del puerto y no de Mongo para mantener el centro limpio
        this.franquiciaRepositoryPort = franquiciaRepositoryPort;
    }

    public Mono<Franquicia> execute(String franquiciaId, String sucursalId, String nombreProducto, int stock) {
        //  busco la franquicia
        //  busco la sucursal dentro de la franquicia
        //  creo el producto y lo agrego
        //  guardo la franquicia
        return franquiciaRepositoryPort.findById(franquiciaId)
                .switchIfEmpty(Mono.error(new NotFoundException("franquicia no existe")))
                .map(franquicia -> {
                    Sucursal sucursal = franquicia.obtenerSucursalPorId(sucursalId);
                    String productoId = UUID.randomUUID().toString();
                    sucursal.agregarProducto(new Producto(productoId, nombreProducto, stock));
                    return franquicia;
                })
                .flatMap(franquiciaRepositoryPort::save);
    }
}
