FROM openjdk:17-jdk-alpine 
WORKDIR /app

ADD ./target/msvc-products-0.0.1-SNAPSHOT.jar msvc-products.jar

# el entrypoint es el punto de entrada cuando colocamos el DockerRun
# para ejecutar a partir de una imagen leantar un contenedor
ENTRYPOINT ["java", "-jar", "msvc-products.jar"]

