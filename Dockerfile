FROM maven:3.6-openjdk-11-slim AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn clean package

FROM openjdk:11-jdk-slim AS spring-boot-suggestion-track
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/app.jar /app/
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
