
# 📡 Microservicio Eureka Server

Este microservicio `eureka-server` actúa como **registro de servicios** dentro de una arquitectura distribuida basada en Spring Cloud.  
Permite que los microservicios se registren y descubran entre sí dinámicamente, facilitando la comunicación y el balanceo de carga.

---

## 🚀 Tecnologías

- **Spring Boot 3.3.7**
- **Spring Cloud Netflix Eureka Server**
- **Java 17**
- **Docker**
- **Maven**

---

## 📦 Funcionalidades Clave

- Registro centralizado de microservicios
- Descubrimiento dinámico de servicios
- Integración con Spring Cloud LoadBalancer
- Configuración simple vía `application.properties`
- Contenerización con Docker

---

## ⚙️ Configuración

### `application.properties`

```properties
spring.application.name=eureka-server
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```
---
## 🚀 Punto de Entrada
```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
```
---
## 🐳 Docker
```Dockerfile
FROM openjdk:17-jdk-alpine 
WORKDIR /app
EXPOSE 8761
ADD ./target/eureka-server-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java", "-jar", "eureka-server.jar"]
```
---
## 🧪 Cómo Ejecutar
### 1. Compilar el proyecto:
```
mvn clean package
```
### 2. Construir la imagen Docker:
```
docker build -t eureka-server .
```
### 3. Ejecutar el contenedor:
```
docker run -p 8761:8761 eureka-server
```
Accede al dashboard de Eureka en: http://localhost:8761
---
👨‍💻 Autor
Freyder Otalvaro Desarrollador Senior Java | Arquitecto Backend | AWS Learner



