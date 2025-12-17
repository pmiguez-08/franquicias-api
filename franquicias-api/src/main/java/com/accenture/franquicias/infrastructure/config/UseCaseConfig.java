
package com.accenture.franquicias.infrastructure.config;

import com.accenture.franquicias.application.usecase.*;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CrearFranquiciaUseCase crearFranquiciaUseCase(FranquiciaRepositoryPort port) {
        // Yo registro el caso de uso como bean para que el controller lo pueda inyectar
        return new CrearFranquiciaUseCase(port);
    }

    @Bean
    public AgregarSucursalAFranquiciaUseCase agregarSucursalAFranquiciaUseCase(FranquiciaRepositoryPort port) {
        return new AgregarSucursalAFranquiciaUseCase(port);
    }

    @Bean
    public AgregarProductoASucursalUseCase agregarProductoASucursalUseCase(FranquiciaRepositoryPort port) {
        return new AgregarProductoASucursalUseCase(port);
    }

    @Bean
    public EliminarProductoDeSucursalUseCase eliminarProductoDeSucursalUseCase(FranquiciaRepositoryPort port) {
        return new EliminarProductoDeSucursalUseCase(port);
    }

    @Bean
    public ActualizarStockProductoUseCase actualizarStockProductoUseCase(FranquiciaRepositoryPort port) {
        return new ActualizarStockProductoUseCase(port);
    }

    @Bean
    public ObtenerProductoConMasStockPorSucursalUseCase obtenerProductoConMasStockPorSucursalUseCase(FranquiciaRepositoryPort port) {
        return new ObtenerProductoConMasStockPorSucursalUseCase(port);
    }

    @Bean
    public ActualizarNombreFranquiciaUseCase actualizarNombreFranquiciaUseCase(FranquiciaRepositoryPort port) {
        // Registra el caso de uso para cambiar el nombre de una franquicia
        return new ActualizarNombreFranquiciaUseCase(port);
    }

    @Bean
    public ActualizarNombreSucursalUseCase actualizarNombreSucursalUseCase(FranquiciaRepositoryPort port) {
        return new ActualizarNombreSucursalUseCase(port);
    }

    @Bean
    public ActualizarNombreProductoUseCase actualizarNombreProductoUseCase(FranquiciaRepositoryPort port) {
        return new ActualizarNombreProductoUseCase(port);
    }
}
