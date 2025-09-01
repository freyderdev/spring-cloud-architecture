FROM openjdk:17-jdk-alpine 
WORKDIR /app

EXPOSE 9411
ADD ./zipkin-server-3.4.4-exec.jar zipkin-server.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "zipkin-server.jar"]

