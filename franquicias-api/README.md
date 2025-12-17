# Franquicias API

**Autor:** **Ingeniero Pablo Miguez**

---

##  Descripción general

Este proyecto implementa un **API REST reactivo** para la gestión de franquicias.

Una **franquicia** contiene **sucursales** y cada **sucursal** contiene **productos con stock**.

La solución fue desarrollada siguiendo principios de **Clean Architecture**, **programación reactiva**, **buenas prácticas de diseño**, **pruebas unitarias**, **contenedorización con Docker** y **despliegue completo en AWS** utilizando **Infrastructure as Code con Terraform**.

El proyecto cumple con **todos los requerimientos obligatorios** y además implementa **todos los puntos extra solicitados**.

---

##  Tecnologías utilizadas

* Java 17
* Spring Boot 3 + WebFlux
* Programación reactiva con **Project Reactor**
* MongoDB / Amazon **DocumentDB** (driver compatible)
* Maven
* Docker y Docker Compose
* Terraform (IaC)
* AWS: ECR, ECS Fargate, DocumentDB, IAM, VPC
* JUnit 5, Mockito, Reactor Test
* Git y GitHub

---

##  Arquitectura

El proyecto sigue el enfoque de **Clean Architecture**, separando responsabilidades en capas bien definidas:

### Domain

* Contiene el **modelo de negocio puro**
* Entidades, value objects y puertos
* No depende de frameworks ni infraestructura

### Application

* Casos de uso (Use Cases)
* Orquesta el dominio
* Contiene la lógica de negocio

### Infrastructure

* Adaptadores técnicos
* Controladores REST
* Persistencia MongoDB / DocumentDB
* Configuración Spring
* Manejo de errores
* Integración con AWS

Esta arquitectura facilita:

* Pruebas unitarias
* Escalabilidad
* Mantenibilidad
* Evolución del sistema

---

##  Funcionalidades implementadas

### Funcionalidades obligatorias

* Crear una franquicia
* Agregar una sucursal a una franquicia
* Agregar un producto a una sucursal
* Eliminar un producto de una sucursal
* Modificar el stock de un producto
* Obtener el producto con mayor stock por sucursal para una franquicia

---

##  Funcionalidades PLUS implementadas

Todos los puntos extra solicitados fueron **implementados y validados**:

###  Programación reactiva

* API completamente reactiva usando **Spring WebFlux**
* Uso de `Mono` y `Flux`
* Persistencia reactiva

###  Contenerización con Docker

* Dockerfile optimizado
* Imagen lista para producción
* Docker Compose para entorno local

###  Endpoints adicionales (PLUS)

Se implementaron endpoints para **actualizar nombres**, tal como se solicitó:

* Actualizar nombre de franquicia
* Actualizar nombre de sucursal
* Actualizar nombre de producto

###  Infrastructure as Code (IaC)

* Aprovisionamiento de infraestructura con **Terraform**
* Recursos creados:

  * Amazon DocumentDB
  * Subnet Group
  * Security Group
* Variables y outputs documentados

###  Despliegue completo en la nube (AWS)

* Imagen Docker publicada en **Amazon ECR**
* Aplicación desplegada en **ECS Fargate**
* Base de datos en **Amazon DocumentDB**
* Configuración de TLS para DocumentDB
* Aplicación accesible por IP pública
* Pruebas realizadas con `curl`

---

##  Ejecución local sin Docker

### 1. Levantar MongoDB

```bash
docker run -d -p 27017:27017 --name mongo-local mongo:7
```

### 2. Ejecutar la aplicación

```bash
mvn clean test
mvn spring-boot:run
```

API disponible en:

```
http://localhost:8080
```

---

##  Ejecución con Docker (local)

```bash
docker compose up -d --build
```

Servicios levantados:

* MongoDB
* Franquicias API

---

##  Despliegue en AWS (PLUS)

### Arquitectura en AWS

* **ECR**: Repositorio de imágenes Docker
* **ECS Fargate**: Ejecución de la API sin servidores
* **DocumentDB**: Persistencia de datos
* **IAM**: Roles para ECS
* **Terraform**: Infraestructura como código

### Verificación en AWS

Health check:

```bash
curl -i http://<IP_PUBLICA>:8080/actuator/health
```

Respuesta esperada:

```json
{"status":"UP"}
```

Crear franquicia en AWS:

```bash
curl -i -X POST http://<IP_PUBLICA>:8080/api/franquicias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Franquicia AWS OK"}'
```

Respuesta esperada:

```json
{
  "id": "...",
  "nombre": "Franquicia AWS OK",
  "sucursales": []
}
```

---

##  Endpoints de actualización (PLUS)

### Actualizar nombre de franquicia

```bash
PATCH /api/franquicias/{franquiciaId}
```

### Actualizar nombre de sucursal

```bash
PATCH /api/franquicias/{franquiciaId}/sucursales/{sucursalId}
```

### Actualizar nombre de producto

```bash
PATCH /api/franquicias/{franquiciaId}/sucursales/{sucursalId}/productos/{productoId}
```

---

##  Pruebas unitarias

Las pruebas:

* No dependen de Mongo real
* Cubren casos de uso principales
* Validan reglas de negocio

```bash
mvn clean test
```

---

##  Manejo de errores

* `400 Bad Request`: Datos inválidos
* `404 Not Found`: Recursos inexistentes
* `409 Conflict`: Reglas de negocio

Errores devueltos en formato JSON descriptivo.

---

##  Estado final del proyecto

✔ Requerimientos obligatorios completos
✔ Todos los puntos PLUS implementados
✔ Dockerización completa
✔ Infraestructura como código
✔ Despliegue exitoso en AWS
✔ Pruebas funcionales y técnicas exitosas

**Proyecto listo para evaluación técnica.**

---

##  Autor

**Ingeniero Pablo Miguez**


