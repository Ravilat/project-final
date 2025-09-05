
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean install -DskipTests -P prod

FROM eclipse-temurin:17-jdk-jammy

ARG JAR_FILE=target/*.jar

COPY --from=build /app/${JAR_FILE} app.jar

COPY resources ./resources

USER root

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]