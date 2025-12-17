FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn -q -e -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q clean test package

FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update && apt-get install -y curl ca-certificates && rm -rf /var/lib/apt/lists/*

# Descarga el bundle global recomendado por AWS para DocumentDB
RUN curl -sS https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem -o /app/global-bundle.pem

# Crea un truststore propio y mete TODOS los certificados del bundle (no solo el primero)
RUN rm -f /app/truststore.jks \
 && awk 'BEGIN {c=0} /BEGIN CERTIFICATE/ {out="/app/cert-" c ".pem"} {print > out} /END CERTIFICATE/ {c++}' /app/global-bundle.pem \
 && for f in /app/cert-*.pem; do \
      a=$(basename "$f" .pem); \
      keytool -importcert -noprompt -trustcacerts \
        -alias "aws-$a" \
        -file "$f" \
        -keystore /app/truststore.jks \
        -storepass changeit; \
    done \
 && keytool -list -keystore /app/truststore.jks -storepass changeit | head -n 50

# Fuerza a Java a usar este truststore
ENV JAVA_TOOL_OPTIONS="-Djavax.net.ssl.trustStore=/app/truststore.jks -Djavax.net.ssl.trustStorePassword=changeit -Djavax.net.ssl.trustStoreType=JKS"

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
