#  🧠 Spring Cloud Microservices Showcase

This project implements a distributed architecture based on microservices using **Spring Boot 3** and **Spring Cloud**.  
It is designed to demonstrate best practices in backend development, scalability, security, and traceability, integrating multiple components of the cloud-native ecosystem.

---

## 🚀 Technologies and Tools

- **Spring Boot 3**, **Spring Cloud Gateway**, **Eureka Server**  
- **Spring Cloud Config Server**, **Spring Cloud LoadBalancer**  
- **OAuth2.1**, **JWT**, **Spring Security**  
- **Micrometer Tracing**, **Zipkin**  
- **Spring Data JPA**, **Hibernate**, **MySQL 8**  
- **WebClient**, **Feign**  
- **Docker**, **Docker Compose**  
- **AWS EC2** (optional deployment)

---

## 🧩 Included Microservices

- `eureka-server`: Service registry and discovery  
- `config-server`: Centralized configuration  
- `msvc-gateway-server`: API Gateway with OAuth2 security  
- `msvc-products`: Product management  
- `msvc-users`: User management  
- `msvc-oauth`: Authentication service  
- `libs-msvc-commons`: Shared libraries  
- `zipkin`: Distributed tracing  
- `docker-compose`: Service orchestration

---

## 📦 Key Features

- Inter-service communication via REST  
- Dynamic load balancing  
- Fault tolerance with Resilience4J  
- Centralized security at the API Gateway  
- Externalized and versioned configuration  
- Distributed tracing with Zipkin  
- Full containerization with Docker  
- Ready for deployment on AWS EC2

---


## 👨‍💻 Author
Freyder Otalvaro
Senior Java Developer | Arquitecto Backend | AWS Learner  
[GitHub](https://github.com/freyderdev)  
Colombia 🇨🇴


## 🧪 How to Run

```bash
docker-compose up --build
