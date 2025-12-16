# Usa una imagen que tiene Maven y Java 17 para compilar el proyecto
FROM maven:3.9-eclipse-temurin-17 AS build

# Crea una carpeta dentro de la imagen para trabajar
WORKDIR /app

# Copia el pom primero para aprovechar cache de dependencias
COPY pom.xml .

# Descarga dependencias antes de copiar el código para acelerar builds siguientes
RUN mvn -q -e -DskipTests dependency:go-offline

# Copia el resto del proyecto
COPY src ./src

# Compila y ejecuta tests para garantizar que la imagen solo se construye si todo está bien
RUN mvn -q clean test package

# Imagen final más liviana solo con Java 17 para ejecutar el jar
FROM eclipse-temurin:17-jre

# Crea carpeta de ejecución
WORKDIR /app

# Copia el jar construido desde la etapa anterior
# Ajusta el nombre si tu jar tiene un nombre diferente en target
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto donde corre Spring Boot
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java","-jar","app.jar"]
