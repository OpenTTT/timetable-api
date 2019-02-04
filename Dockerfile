# Build actual backend container
FROM openjdk:11-jre
COPY --from=BUILD_ENV "/app/target/openttt-backend-1.0-SNAPSHOT.jar" "/app/openttt.jar"
CMD ["/usr/bin/java", "-jar", "/app/openttt.jar"]