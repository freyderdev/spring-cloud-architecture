FROM openjdk:17-jdk-alpine 
WORKDIR /app

EXPOSE 8005
ADD ./target/msvc-items-0.0.1-SNAPSHOT.jar msvc-items.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "msvc-items.jar"]

