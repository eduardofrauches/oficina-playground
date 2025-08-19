# -------- build --------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia pom.xml e baixa dependências
COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

# Copia o código-fonte
COPY src ./src

# Build do JAR
RUN mvn -q -DskipTests clean package

# -------- runtime -------
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/oficina-playground-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker
ENTRYPOINT ["java","-jar","/app/app.jar"]
