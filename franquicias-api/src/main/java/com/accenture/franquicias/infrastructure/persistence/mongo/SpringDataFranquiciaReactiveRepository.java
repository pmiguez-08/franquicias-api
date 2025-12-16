package com.accenture.franquicias.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataFranquiciaReactiveRepository extends ReactiveMongoRepository<FranquiciaDocument, String> {
    // heredo métodos reactivos como save y findById sin implementarlos
}
