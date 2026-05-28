# Etapa 1: Build (Bucătăria unde se compilează codul)
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiem fișierele de configurare și codul sursă
COPY pom.xml .
COPY src ./src

# Construim fișierul .jar (sărim testele ca să meargă mai repede pe Railway)
RUN mvn clean package -DskipTests

# Etapa 2: Run (Imaginea finală, ușoară, care va rula în cloud)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Luăm fișierul generat anterior și îl redenumim app.jar
COPY --from=build /app/target/*.jar app.jar

# Deschiem portul 8081 (cel pe care îl folosești tu)
EXPOSE 8081

# Comanda de pornire
ENTRYPOINT ["java", "-jar", "app.jar"]