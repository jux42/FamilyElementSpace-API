FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
COPY --chown=1000:1000 ./src/main/resources/application-familyspace-configuration.yml /src/main/resources/application-familyspace-configuration.yml
EXPOSE 8080
ENV SPRING_CONFIG_ADDITIONAL_LOCATION=file:/src/main/resources/
ENTRYPOINT ["java", "-jar", "app.jar"]