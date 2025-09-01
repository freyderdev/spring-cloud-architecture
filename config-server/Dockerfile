FROM openjdk:17-jdk-alpine 
WORKDIR /app

EXPOSE 8888
ADD ./target/config-server-0.0.1-SNAPSHOT.jar config-server.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "config-server.jar"]

