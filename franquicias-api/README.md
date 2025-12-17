

# Franquicias API

**Autor:** Ingeniero Pablo Miguez

## Descripción general

Este proyecto implementa un API REST reactivo para la gestión de franquicias.
Una franquicia contiene sucursales y cada sucursal contiene productos con stock.
La solución fue desarrollada siguiendo principios de Clean Architecture, programación reactiva, buenas prácticas de diseño, pruebas unitarias y contenedorización con Docker.


---

## Tecnologías utilizadas

* Java 17
* Spring Boot con WebFlux
* Programación reactiva con Project Reactor
* MongoDB (reactivo)
* Maven
* Docker y Docker Compose
* JUnit 5, Mockito y Reactor Test
* Git y GitHub

---

## Arquitectura

El proyecto sigue Clean Architecture, separando responsabilidades en capas claras.

```bash
FRANQUICIAS-API/
├─ franquicias-api/
│  ├─ .mvn/
│  │  └─ wrapper/
│  ├─ iac/
│  │  └─ terraform/
│  │     ├─ main.tf
│  │     ├─ providers.tf
│  │     ├─ variables.tf
│  │     ├─ outputs.tf
│  │     ├─ terraform.tfvars
│  │     ├─ terraform.tfvars.example
│  │     └─ terraform.tfstate*
│  ├─ src/
│  │  ├─ main/
│  │  │  ├─ java/com/accenture/franquicias/
│  │  │  │  ├─ domain/
│  │  │  │  ├─ application/
│  │  │  │  ├─ infrastructure/
│  │  │  │  └─ FranquiciasApiApplication.java
│  │  │  └─ resources/
│  │  └─ test/
│  │     └─ java/com/accenture/franquicias/
│  │        ├─ domain/
│  │        ├─ application/
│  │        ├─ infrastructure/
│  │        └─ FranquiciasApiApplicationTests.java
│  ├─ Dockerfile
│  ├─ docker-compose.yml
│  ├─ ecs-task-def.json
│  ├─ ecs-task-execution-trust.json
│  ├─ pom.xml
│  └─ README.md
```

* **Domain**
  Contiene el modelo de negocio puro y los puertos. No depende de frameworks ni infraestructura.

* **Application**
  Contiene los casos de uso que representan las acciones del sistema. Orquesta el dominio y utiliza los puertos.

* **Infrastructure**
  Contiene adaptadores técnicos como MongoDB, controladores web, configuración de Spring y manejo de errores.

Esta separación permite un código más fácil de probar, mantener y extender.

---

## Funcionalidades implementadas

### Endpoints obligatorios

* Crear una franquicia
* Agregar una sucursal a una franquicia
* Agregar un producto a una sucursal
* Eliminar un producto de una sucursal
* Modificar el stock de un producto
* Obtener, para una franquicia, el producto con mayor stock por cada sucursal

### Características técnicas

* Programación reactiva de extremo a extremo
* Persistencia con MongoDB reactivo
* Pruebas unitarias de los casos de uso
* Contenerización completa con Docker
* Manejo centralizado de errores HTTP

---

## Requisitos previos

Para ejecutar el proyecto se necesita:

* Java 17
* Maven
* Docker y Docker Compose
* Git

---

## Ejecución local sin Docker

### 1. Levantar MongoDB

Se puede usar MongoDB local o Docker.

Con Docker:

```bash
docker run -d -p 27017:27017 --name mongo-local mongo:7
```

### 2. Ejecutar la aplicación

```bash
mvn clean test
mvn spring-boot:run
```

La API quedará disponible en:

```
http://localhost:8080
```

---

## Ejecución con Docker (recomendado)

### 1. Construir y levantar todo

Desde la raíz del proyecto:

```bash
docker compose up -d --build
```

Esto levanta:

* MongoDB
* La API de franquicias

### 2. Verificar contenedores

```bash
docker ps
```

### 3. Detener servicios

```bash
docker compose down
```

---

## Ejecución de pruebas unitarias

Las pruebas unitarias cubren los casos de uso principales y no dependen de Mongo real.

```bash
mvn clean test
```

Todas las pruebas deben finalizar en **BUILD SUCCESS**.

---

## Endpoints y ejemplos de uso

### Crear franquicia

```bash
curl -i -X POST http://localhost:8080/api/franquicias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Mi Franquicia"}'
```

### Agregar sucursal

```bash
curl -i -X POST http://localhost:8080/api/franquicias/{franquiciaId}/sucursales \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Sucursal Centro"}'
```

### Agregar producto

```bash
curl -i -X POST http://localhost:8080/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Producto A","stock":50}'
```

### Actualizar stock

```bash
curl -i -X PATCH http://localhost:8080/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}/stock \
  -H "Content-Type: application/json" \
  -d '{"stock":120}'
```

### Eliminar producto

```bash
curl -i -X DELETE http://localhost:8080/api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}
```

### Obtener producto con mayor stock por sucursal

```bash
curl -i http://localhost:8080/api/franquicias/{franquiciaId}/productos-top-stock-por-sucursal
```

---

## Manejo de errores

* 400 Bad Request para datos inválidos
* 404 Not Found cuando una franquicia, sucursal o producto no existe
* 409 Conflict para violaciones de reglas de negocio

Las respuestas de error se devuelven en formato JSON con un mensaje descriptivo.

---

## Estado actual del proyecto

* Funcionalidades obligatorias completas
* Programación reactiva aplicada
* Pruebas unitarias implementadas
* Dockerización completa
* Listo para evaluación técnica

---

## Próximos pasos (Plus)

* Endpoints para actualizar nombres de franquicia, sucursal y producto
* Infrastructure as Code con Terraform o CloudFormation
* Pipeline CI/CD
* Despliegue en la nube

---

## Autor

**Ingeniero Pablo Miguez**


