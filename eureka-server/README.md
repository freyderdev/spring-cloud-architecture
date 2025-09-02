
# 📡 Eureka Server Microservice

The `eureka-server` microservice acts as a **service registry** within a distributed architecture based on Spring Cloud.  
It allows microservices to register and discover each other dynamically, enabling seamless communication and load balancing.

---

## 🚀 Technologies

- **Spring Boot 3.3.7**
- **Spring Cloud Netflix Eureka Server**
- **Java 17**
- **Docker**
- **Maven**

---

## 📦 Key Features

- Centralized microservice registry  
- Dynamic service discovery  
- Integration with Spring Cloud LoadBalancer  
- Simple configuration via `application.properties`  
- Docker containerization

---

## ⚙️ Configuration

### `application.properties`

```properties
spring.application.name=eureka-server
server.port=8761
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```
---
## 🚀 Entry Point
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
## 🧪 How to Run
### 1. Build the project:
```
mvn clean package
```
### 2. Build the Docker image:
```
docker build -t eureka-server .
```
### 3. . Run the container:
```
docker run -p 8761:8761 eureka-server
```
Access the Eureka dashboard at: http://localhost:8761
---
👨‍💻 Autor
Freyder Otalvaro Desarrollador Senior Java | Architec Backend | AWS Learner



