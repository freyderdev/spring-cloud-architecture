
# 🚪 Gateway Server Microservice

The `msvc-gateway-server` microservice serves as the **entry point** to the distributed architecture, managing routing, security, load balancing, and observability.  
It is built with **Spring Cloud Gateway MVC**, and integrates with Eureka, Zipkin, OAuth2, and Resilience4J to provide a robust and extensible solution.

---

## 🚀 Technologies

- Spring Boot 3.3.7  
- Spring Cloud Gateway MVC  
- Spring Security OAuth2 + JWT  
- Spring Cloud Netflix Eureka Client  
- Resilience4J (CircuitBreaker + TimeLimiter)  
- Micrometer Tracing + Zipkin  
- Docker  
- Maven

---

## 📦 Key Features

- Enrutamiento dinámico hacia microservicios (`msvc-products`, `msvc-items`, `msvc-users`, `msvc-oauth`)
- Filtros personalizados y globales (`SampleGlobalFilter`)
- Balanceo de carga con Spring Cloud LoadBalancer
- Circuit Breaker con fallback automático
- Seguridad basada en roles extraídos desde JWT
- Integración con Eureka para descubrimiento de servicios
- Trazabilidad distribuida con Zipkin

---

## 🔐 Security

Configured in `SecurityConfig.java`:

- Public access to `/authorized` and `/logout`  
- Role-based protection for `/api/**` routes (`ADMIN`, `USER`)  
- Role conversion from JWT to `GrantedAuthority`  
- OAuth2 login and JWT token validation  
- Stateless session management

---

## 🧭 Routing

Defined in `routerConfig()` and `application.yml`:

- Configured routes:
  - `/api/products/**`
  - `/api/items/**`
  - `/api/users/**`
  - `/api/security/**`
- Applied filters:
  - `StripPrefix`, `AddRequestHeader`, `AddResponseHeader`, `AddRequestParameter`
  - `CircuitBreaker` con fallback a `/api/items/5`
  - `LoadBalancer` to services registered in Eureka

---

## ⚙️ Configuration

### `application.yml`

- Resilience4J settings:
  - CircuitBreaker with a 6-request window and 50% failure threshold  
  - TimeLimiter with a 3-second timeout
- OAuth2 settings:
  - `client-app` with `client-id`, `client-secret`, `redirect-uri`, `scopes`  
  - `issuer-uri` parameterized for Docker
- Gateway route configuration with filters and predicates
- Zipkin integration:
  ```yaml
  management.zipkin.tracing.endpoint: http://zipkin-server:9411/api/v2/spans
  management.tracing.sampling.probability: 1.0
  ```
---

## 🐳 Docker
```Dockerfile
  FROM openjdk:17-jdk-alpine 
  WORKDIR /app
  EXPOSE 8090
  ADD ./target/msvc-gateway-server-0.0.1-SNAPSHOT.jar msvc-gateway-server.jar
  ENTRYPOINT ["java", "-jar", "msvc-gateway-server.jar"]
  ```

---

## 🧪 How to Run
---
1 -  Build the project:
```
mvn clean package
```
2 - Build the Docker image:
```
  docker build -t msvc-gateway-server .
```
3 - Run the container:
```
  docker run -p 8090:8090 msvc-gateway-server
```
Access the gateway at: http://localhost:8090
---

## 📁 Dependencies (pom.xml)
### Includes starters for:
- Spring Cloud Gateway MVC
- Eureka Client
- OAuth2 Client y Resource Server
- CircuitBreaker con Resilience4J
- Micrometer Tracing + Zipkin
- Spring Security y Actuator
  
---

👨‍💻 Autor
Freyder Otalvaro
|Desarrollador Senior Java|
|AWS Learner|




