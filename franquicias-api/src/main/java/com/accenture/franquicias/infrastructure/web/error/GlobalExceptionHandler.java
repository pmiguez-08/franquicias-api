package com.accenture.franquicias.infrastructure.web.error;

import com.accenture.franquicias.domain.model.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public Mono<org.springframework.http.ResponseEntity<ApiErrorResponse>> handleNotFound(NotFoundException ex) {
        // Responde 404 cuando un recurso no existe
        return Mono.just(org.springframework.http.ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<org.springframework.http.ResponseEntity<ApiErrorResponse>> handleBadRequest(IllegalArgumentException ex) {
        // Responde 400 cuando llegan datos inválidos
        return Mono.just(org.springframework.http.ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(ex.getMessage())));
    }

    @ExceptionHandler(IllegalStateException.class)
    public Mono<org.springframework.http.ResponseEntity<ApiErrorResponse>> handleConflict(IllegalStateException ex) {
        // Responde 409 cuando la operación no se puede completar por reglas del negocio
        return Mono.just(org.springframework.http.ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(ex.getMessage())));
    }
}
