
# 🛠️ Config Server Microservice

The `config-server` microservice acts as a **centralized configuration server** for distributed Spring Boot applications.  
It provides externalized configuration from a Git repository, enabling dynamic updates and environment-specific settings across microservices.

---

## 🚀 Technologies

- Spring Boot 3.4.2  
- Spring Cloud Config Server  
- Java 17  
- Docker  
- Maven

---

## 📦 Key Features

- Centralized configuration management for microservices  
- Git-based configuration repository  
- Dynamic refresh support via Spring Cloud  
- Easy integration with Eureka, Gateway, and other services  
- Lightweight and container-ready

---

## ⚙️ Configuration

### `application.properties`

```properties
spring.application.name=config-server
server.port=8888
spring.cloud.config.server.git.uri=https://github.com/FooandV/msvc-items-config.git
```
The server reads configuration files from the specified Git repository and serves them to client microservices.
---

## 🚀 Entry Point
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
## 🧪 How to Run
1- Build the project:
```
mvn clean package
```
2- Build the Docker image:
```
docker build -t config-server .
```
3- Run the container:
```
docker run -p 8888:8888 config-server
```
Access the config server at: http://localhost:8888

---
## 📁 Dependencies (pom.xml)
### Includes:
- Spring Cloud Config Server
- Spring Boot DevTools
- Spring Boot Starter Test
- Spring Cloud BOM (2024.0.0) for dependency management
---
👨‍💻 Author
Freyder Otalvaro Senior | Java Developer | Backend Architect | AWS Learner





