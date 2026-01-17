FROM maven:3.8.6-openjdk-17 AS builder 

WORKDIR /build 
COPY pom.xml . 
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app
COPY --chown=appuser:appgroup --chmod=755 --from=builder /build/target/*.jar app.jar 
EXPOSE 8080
CMD ["java" ,"-jar" , "app.jar"]