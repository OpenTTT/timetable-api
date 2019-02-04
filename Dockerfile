FROM maven:3.6.0-jdk-11 AS BUILD_ENV
WORKDIR /app/

# Optimizing for faster build times by having the dependency downloaded in a layer before the sources
COPY pom.xml pom.xml
RUN mvn dependency:go-offline -B

# Build artifact
COPY src src
# Tests are assumed to be working when building an image
RUN mvn -B package -DskipTests

# Build actual backend container
FROM openjdk:11-jre
COPY --from=BUILD_ENV "/app/target/openttt-backend-1.0-SNAPSHOT.jar" "/app/openttt.jar"
CMD ["/usr/bin/java", "-jar", "/app/openttt.jar"]