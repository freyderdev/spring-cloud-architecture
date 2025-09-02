
# 🚪 Microservicio Gateway Server

Este microservicio `msvc-gateway-server` actúa como **puerta de entrada** a la arquitectura distribuida, gestionando el enrutamiento, la seguridad, el balanceo de carga y la trazabilidad.  
Está construido con **Spring Cloud Gateway MVC**, y se integra con Eureka, Zipkin, OAuth2 y Resilience4J para ofrecer una solución robusta y extensible.

---

## 🚀 Tecnologías

- Spring Boot 3.3.7  
- Spring Cloud Gateway MVC  
- Spring Security OAuth2 + JWT  
- Spring Cloud Netflix Eureka Client  
- Resilience4J (CircuitBreaker + TimeLimiter)  
- Micrometer Tracing + Zipkin  
- Docker  
- Maven

---

## 📦 Funcionalidades Clave

- Enrutamiento dinámico hacia microservicios (`msvc-products`, `msvc-items`, `msvc-users`, `msvc-oauth`)
- Filtros personalizados y globales (`SampleGlobalFilter`)
- Balanceo de carga con Spring Cloud LoadBalancer
- Circuit Breaker con fallback automático
- Seguridad basada en roles extraídos desde JWT
- Integración con Eureka para descubrimiento de servicios
- Trazabilidad distribuida con Zipkin

---

## 🔐 Seguridad

Configurada en `SecurityConfig.java`:

- Acceso público a `/authorized` y `/logout`
- Protección de rutas `/api/**` según roles (`ADMIN`, `USER`)
- Conversión de roles desde JWT a `GrantedAuthority`
- Login OAuth2 y validación de tokens JWT
- Stateless session management

---

## 🧭 Enrutamiento

Definido en `routerConfig()` y `application.yml`:

- Rutas configuradas:
  - `/api/products/**`
  - `/api/items/**`
  - `/api/users/**`
  - `/api/security/**`
- Filtros aplicados:
  - `StripPrefix`, `AddRequestHeader`, `AddResponseHeader`, `AddRequestParameter`
  - `CircuitBreaker` con fallback a `/api/items/5`
  - `LoadBalancer` hacia servicios registrados en Eureka

---

## ⚙️ Configuración

### `application.yml`

- Configuración de Resilience4J:
  - CircuitBreaker con ventana de 6 requests y umbral de error del 50%
  - TimeLimiter con timeout de 3 segundos
- Configuración OAuth2:
  - `client-app` con `client-id`, `client-secret`, `redirect-uri`, `scopes`
  - `issuer-uri` parametrizado para Docker
- Configuración de rutas del Gateway con filtros y predicados
- Integración con Zipkin:
  ```yaml
  management.zipkin.tracing.endpoint: http://zipkin-server:9411/api/v2/spans
  management.tracing.sampling.probability: 1.0
```
---
## 🐳 Docker
```
FROM openjdk:17-jdk-alpine 
WORKDIR /app
EXPOSE 8090
ADD ./target/msvc-gateway-server-0.0.1-SNAPSHOT.jar msvc-gateway-server.jar
ENTRYPOINT ["java", "-jar", "msvc-gateway-server.jar"]
```
---
## 🧪 Cómo Ejecutar
1 -  Compilar el proyecto:
```
mvn clean package
```
2 - Construir la imagen Docker:
```
docker build -t msvc-gateway-server .
```
3 - Ejecutar el contenedor:
```
docker run -p 8090:8090 msvc-gateway-server
```
Accede al gateway en: http://localhost:8090
---
## 📁 Dependencias (pom.xml)
###Incluye starters para:
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



