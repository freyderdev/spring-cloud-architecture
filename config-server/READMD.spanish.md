
# 🛠️ Microservicio Config Server

El microservicio `config-server` funciona como un **servidor de configuración centralizado** para aplicaciones distribuidas basadas en Spring Boot.  
Proporciona configuración externalizada desde un repositorio Git, permitiendo actualizaciones dinámicas y ajustes específicos por entorno para todos los microservicios.

---

## 🚀 Tecnologías

- Spring Boot 3.4.2  
- Spring Cloud Config Server  
- Java 17  
- Docker  
- Maven

---

## 📦 ncionalidades Clave

- Gestión centralizada de configuración para microservicios  
- Repositorio de configuración basado en Git  
- Soporte para actualización dinámica con Spring Cloud  
- Integración sencilla con Eureka, Gateway y otros servicios  
- Ligero y listo para contenedores

---

## ⚙️ Configuración

### `application.properties`

```properties
spring.application.name=config-server
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/freyderdev/msvc-items-config.git
```
El servidor lee los archivos de configuración desde el repositorio Git especificado y los entrega a los microservicios cliente.
---

## 🚀 Punto de Entrada
```java
@EnableConfigServer
@SpringBootApplication
public class ConfigServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
```
---
## 🐳 Docker
```Dockerfile
FROM openjdk:17-jdk-alpine 
WORKDIR /app
EXPOSE 8888
ADD ./target/config-server-0.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java", "-jar", "config-server.jar"]
```

---
## 🧪 Cómo Ejecutar
1- Compilar el proyecto:
```
mvn clean package
```
2- Construir la imagen Docker:
```
docker build -t config-server .
```
3- Ejecutar el contenedor:
```
docker run -p 8888:8888 config-server
```
Accede al servidor de configuración en: http://localhost:8888

---
## 📁 Dependencias (pom.xml)
### Incluye:
- Spring Cloud Config Server
- Spring Boot DevTools
- Spring Boot Starter Test
- Spring Cloud BOM (2024.0.0) para gestión de dependencias
---
👨‍💻 Autor
Freyder Otalvaro Senior | Java Developer | Backend Architect | AWS Learner






