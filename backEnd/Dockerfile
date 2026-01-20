FROM maven:3.8.5-openjdk-17 AS builder 

WORKDIR /build 
COPY pom.xml . 
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine

RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
RUN chmod 555 app.jar 

USER appuser
EXPOSE 8080
CMD ["java" ,"-jar" , "app.jar"]