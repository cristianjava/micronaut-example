FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY build/libs/MicronautExample-*-all.jar MicronautExample.jar
EXPOSE 80
ENTRYPOINT ["java", "-jar", "MicronautExample.jar"]