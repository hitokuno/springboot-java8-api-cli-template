# ---- Build stage ----
FROM maven:3.8-openjdk-8 AS build
WORKDIR /build
COPY pom.xml .
# Download dependencies first (layer cache)
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn clean package -DskipTests -q

# ---- Runtime stage ----
FROM openjdk:8-jre-slim
WORKDIR /app

# Install keytool (included in JRE) and curl for health checks
RUN apt-get update && apt-get install -y --no-install-recommends curl && rm -rf /var/lib/apt/lists/*

COPY --from=build /build/target/app.jar app.jar
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Keystore mount point
VOLUME /app/keystore

EXPOSE ${SERVER_PORT:-8443}

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["java", "-jar", "/app/app.jar"]
