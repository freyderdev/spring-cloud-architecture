FROM openjdk:17-jdk-alpine 
WORKDIR /app

EXPOSE 9100
ADD ./target/msvc-oauth-0.0.1-SNAPSHOT.jar msvc-oauth.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "msvc-oauth.jar"]

