package com.accenture.franquicias.infrastructure.web.controller;

import com.accenture.franquicias.application.usecase.ActualizarNombreFranquiciaUseCase;
import com.accenture.franquicias.application.usecase.ActualizarNombreProductoUseCase;
import com.accenture.franquicias.application.usecase.ActualizarNombreSucursalUseCase;
import com.accenture.franquicias.application.usecase.ActualizarStockProductoUseCase;
import com.accenture.franquicias.application.usecase.AgregarProductoASucursalUseCase;
import com.accenture.franquicias.application.usecase.AgregarSucursalAFranquiciaUseCase;
import com.accenture.franquicias.application.usecase.CrearFranquiciaUseCase;
import com.accenture.franquicias.application.usecase.EliminarProductoDeSucursalUseCase;
import com.accenture.franquicias.application.usecase.ObtenerProductoConMasStockPorSucursalUseCase;
import com.accenture.franquicias.infrastructure.web.dto.ActualizarNombreRequest;
import com.accenture.franquicias.infrastructure.web.dto.ActualizarStockRequest;
import com.accenture.franquicias.infrastructure.web.dto.CrearFranquiciaRequest;
import com.accenture.franquicias.infrastructure.web.dto.CrearProductoRequest;
import com.accenture.franquicias.infrastructure.web.dto.CrearSucursalRequest;
import com.accenture.franquicias.infrastructure.web.dto.FranquiciaResponse;
import com.accenture.franquicias.infrastructure.web.dto.ProductoTopStockPorSucursalResponse;
import com.accenture.franquicias.infrastructure.web.mapper.FranquiciaWebMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franquicias")
public class FranquiciasController {

    private final CrearFranquiciaUseCase crearFranquiciaUseCase;
    private final AgregarSucursalAFranquiciaUseCase agregarSucursalAFranquiciaUseCase;
    private final AgregarProductoASucursalUseCase agregarProductoASucursalUseCase;
    private final EliminarProductoDeSucursalUseCase eliminarProductoDeSucursalUseCase;
    private final ActualizarStockProductoUseCase actualizarStockProductoUseCase;
    private final ObtenerProductoConMasStockPorSucursalUseCase obtenerProductoConMasStockPorSucursalUseCase;

    private final ActualizarNombreFranquiciaUseCase actualizarNombreFranquiciaUseCase;
    private final ActualizarNombreSucursalUseCase actualizarNombreSucursalUseCase;
    private final ActualizarNombreProductoUseCase actualizarNombreProductoUseCase;

    public FranquiciasController(
            CrearFranquiciaUseCase crearFranquiciaUseCase,
            AgregarSucursalAFranquiciaUseCase agregarSucursalAFranquiciaUseCase,
            AgregarProductoASucursalUseCase agregarProductoASucursalUseCase,
            EliminarProductoDeSucursalUseCase eliminarProductoDeSucursalUseCase,
            ActualizarStockProductoUseCase actualizarStockProductoUseCase,
            ObtenerProductoConMasStockPorSucursalUseCase obtenerProductoConMasStockPorSucursalUseCase,
            ActualizarNombreFranquiciaUseCase actualizarNombreFranquiciaUseCase,
            ActualizarNombreSucursalUseCase actualizarNombreSucursalUseCase,
            ActualizarNombreProductoUseCase actualizarNombreProductoUseCase
    ) {
        // Recibe casos de uso listos para ejecutar acciones del negocio
        this.crearFranquiciaUseCase = crearFranquiciaUseCase;
        this.agregarSucursalAFranquiciaUseCase = agregarSucursalAFranquiciaUseCase;
        this.agregarProductoASucursalUseCase = agregarProductoASucursalUseCase;
        this.eliminarProductoDeSucursalUseCase = eliminarProductoDeSucursalUseCase;
        this.actualizarStockProductoUseCase = actualizarStockProductoUseCase;
        this.obtenerProductoConMasStockPorSucursalUseCase = obtenerProductoConMasStockPorSucursalUseCase;

        // Recibe casos de uso extra para sumar puntos actualizando nombres
        this.actualizarNombreFranquiciaUseCase = actualizarNombreFranquiciaUseCase;
        this.actualizarNombreSucursalUseCase = actualizarNombreSucursalUseCase;
        this.actualizarNombreProductoUseCase = actualizarNombreProductoUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaResponse> crearFranquicia(@Valid @RequestBody CrearFranquiciaRequest request) {
        // Recibe el nombre del JSON y crea la franquicia
        return crearFranquiciaUseCase.execute(request.getNombre())
                .map(FranquiciaWebMapper::toResponse);
    }

    @PostMapping("/{franquiciaId}/sucursales")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaResponse> agregarSucursal(
            @PathVariable String franquiciaId,
            @Valid @RequestBody CrearSucursalRequest request
    ) {
        // Agrega una sucursal dentro de una franquicia y devuelve la franquicia actualizada
        return agregarSucursalAFranquiciaUseCase.execute(franquiciaId, request.getNombre())
                .map(FranquiciaWebMapper::toResponse);
    }

    @PostMapping("/{franquiciaId}/sucursales/{sucursalId}/productos")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranquiciaResponse> agregarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody CrearProductoRequest request
    ) {
        // Agrega un producto dentro de una sucursal y devuelve la franquicia actualizada
        return agregarProductoASucursalUseCase.execute(
                        franquiciaId,
                        sucursalId,
                        request.getNombre(),
                        request.getStock()
                )
                .map(FranquiciaWebMapper::toResponse);
    }

    @DeleteMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> eliminarProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId
    ) {
        // Elimina un producto y devuelve 204 porque no se necesita cuerpo de respuesta
        return eliminarProductoDeSucursalUseCase.execute(franquiciaId, sucursalId, productoId);
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizarStock(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody ActualizarStockRequest request
    ) {
        // Actualiza el stock del producto y devuelve 204 al terminar
        return actualizarStockProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getStock());
    }

    @GetMapping("/{franquiciaId}/productos-top-stock-por-sucursal")
    public Flux<ProductoTopStockPorSucursalResponse> obtenerTopStockPorSucursal(@PathVariable String franquiciaId) {
        // Devuelve una lista donde cada elemento representa el producto con más stock de una sucursal
        return obtenerProductoConMasStockPorSucursalUseCase.execute(franquiciaId)
                .map(item -> new ProductoTopStockPorSucursalResponse(
                        item.sucursalId(),
                        item.sucursalNombre(),
                        item.productoId(),
                        item.productoNombre(),
                        item.stock()
                ));
    }

    @PatchMapping("/{franquiciaId}/nombre")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizarNombreFranquicia(
            @PathVariable String franquiciaId,
            @Valid @RequestBody ActualizarNombreRequest request
    ) {
        // Cambia el nombre de la franquicia y devuelve 204 porque no se necesita cuerpo
        return actualizarNombreFranquiciaUseCase.execute(franquiciaId, request.getNombre());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/nombre")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizarNombreSucursal(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @Valid @RequestBody ActualizarNombreRequest request
    ) {
        // Cambia el nombre de la sucursal dentro de la franquicia y devuelve 204
        return actualizarNombreSucursalUseCase.execute(franquiciaId, sucursalId, request.getNombre());
    }

    @PatchMapping("/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/nombre")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> actualizarNombreProducto(
            @PathVariable String franquiciaId,
            @PathVariable String sucursalId,
            @PathVariable String productoId,
            @Valid @RequestBody ActualizarNombreRequest request
    ) {
        // Cambia el nombre del producto dentro de la sucursal y devuelve 204
        return actualizarNombreProductoUseCase.execute(franquiciaId, sucursalId, productoId, request.getNombre());
    }
}
