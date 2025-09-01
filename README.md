# 🧠 Arquitectura de Microservicios con Spring Cloud

Este proyecto implementa una arquitectura distribuida basada en microservicios utilizando Spring Boot 3 y Spring Cloud. Está diseñado para demostrar buenas prácticas en desarrollo backend, escalabilidad, seguridad y trazabilidad, integrando múltiples componentes del ecosistema cloud-native.

## 🚀 Tecnologías y herramientas

- **Spring Boot 3**, **Spring Cloud Gateway**, **Eureka Server**
- **Spring Cloud Config Server**, **Spring Cloud LoadBalancer**
- **OAuth2.1**, **JWT**, **Spring Security**
- **Micrometer Tracing**, **Zipkin**
- **Spring Data JPA**, **Hibernate**, **MySQL 8**
- **WebClient**, **Feign**
- **Docker**, **Docker Compose**
- **AWS EC2** (despliegue opcional)

## 🧩 Microservicios incluidos

- `eureka-server`: Registro y descubrimiento de servicios
- `config-server`: Configuración centralizada
- `msvc-gateway-server`: Puerta de enlace con seguridad OAuth2
- `msvc-products`: Gestión de productos
- `msvc-users`: Gestión de usuarios
- `msvc-oauth`: Servicio de autenticación
- `libs-msvc-commons`: Librerías compartidas
- `zipkin`: Trazabilidad distribuida
- `docker-compose`: Orquestación de servicios

## 📦 Características destacadas

- Comunicación entre microservicios vía REST
- Balanceo de carga dinámico
- Tolerancia a fallos con Resilience4J
- Seguridad centralizada en Gateway
- Configuración externa y versionada
- Trazabilidad distribuida con Zipkin
- Contenerización completa con Docker
- Listo para despliegue en AWS EC2

## 🧪 Cómo ejecutar

```bash
docker-compose up --build
