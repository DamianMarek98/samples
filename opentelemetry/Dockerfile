FROM openjdk:21-slim
COPY build/libs/opentelemetry-1.0.0-SNAPSHOT.jar opentelemetry.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/opentelemetry.jar"]
