package com.accenture.franquicias.infrastructure.persistence.mongo;

import com.accenture.franquicias.domain.model.Franquicia;
import com.accenture.franquicias.domain.model.Producto;
import com.accenture.franquicias.domain.model.Sucursal;
import com.accenture.franquicias.domain.port.FranquiciaRepositoryPort;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class MongoFranquiciaRepositoryAdapter implements FranquiciaRepositoryPort {

    private final SpringDataFranquiciaReactiveRepository springDataRepository;

    public MongoFranquiciaRepositoryAdapter(SpringDataFranquiciaReactiveRepository springDataRepository) {
        // guardo este repositorio porque es el que realmente sabe hablar con Mongo
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Mono<Franquicia> save(Franquicia franquicia) {
        // convierto el modelo del dominio a un documento que Mongo entiende
        FranquiciaDocument document = toDocument(franquicia);

        // guardo en Mongo de forma reactiva y luego convierto de regreso al dominio
        return springDataRepository.save(document)
                .map(this::toDomain);
    }

    @Override
    public Mono<Franquicia> findById(String franquiciaId) {
        //  busco en Mongo por id y si existe lo traduzco al dominio
        return springDataRepository.findById(franquiciaId)
                .map(this::toDomain);
    }

    private FranquiciaDocument toDocument(Franquicia franquicia) {
        //  traduzco franquicia del dominio a documento de Mongo
        List<SucursalDocument> sucursales = franquicia.getSucursales()
                .stream()
                .map(this::toDocument)
                .collect(Collectors.toList());

        return new FranquiciaDocument(
                franquicia.getId(),
                franquicia.getNombre(),
                sucursales
        );
    }

    private SucursalDocument toDocument(Sucursal sucursal) {
        //  traduzco sucursal del dominio a documento embebido
        List<ProductoDocument> productos = sucursal.getProductos()
                .stream()
                .map(this::toDocument)
                .collect(Collectors.toList());

        return new SucursalDocument(
                sucursal.getId(),
                sucursal.getNombre(),
                productos
        );
    }

    private ProductoDocument toDocument(Producto producto) {
        // traduzco producto del dominio a documento embebido
        return new ProductoDocument(
                producto.getId(),
                producto.getNombre(),
                producto.getStock()
        );
    }

    private Franquicia toDomain(FranquiciaDocument document) {
        //  traduzco el documento de Mongo a una franquicia del dominio
        Franquicia franquicia = new Franquicia(document.getId(), document.getNombre());

        for (SucursalDocument sucursalDoc : document.getSucursales()) {
            Sucursal sucursal = new Sucursal(sucursalDoc.getId(), sucursalDoc.getNombre());

            for (ProductoDocument productoDoc : sucursalDoc.getProductos()) {
                Producto producto = new Producto(productoDoc.getId(), productoDoc.getNombre(), productoDoc.getStock());
                sucursal.agregarProducto(producto);
            }

            franquicia.agregarSucursal(sucursal);
        }

        return franquicia;
    }
}
