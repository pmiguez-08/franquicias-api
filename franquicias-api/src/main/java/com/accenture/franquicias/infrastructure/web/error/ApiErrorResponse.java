package com.accenture.franquicias.infrastructure.web.error;

public class ApiErrorResponse {

    private String mensaje;

    public ApiErrorResponse() {
        // Constructor vacío para serialización
    }

    public ApiErrorResponse(String mensaje) {
        // Guarda el mensaje de error
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        // Devuelve el mensaje para el cliente
        return mensaje;
    }
}
