FROM openjdk:17-jdk-alpine 
WORKDIR /app

EXPOSE 8090
ADD ./target/msvc-gateway-server-0.0.1-SNAPSHOT.jar msvc-gateway-server.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "msvc-gateway-server.jar"]

