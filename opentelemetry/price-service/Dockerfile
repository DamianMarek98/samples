FROM openjdk:21-slim
COPY build/libs/price-service-1.0.0-SNAPSHOT.jar price-service.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/price-service.jar"]
