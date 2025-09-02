
# 🔐 Microservicio de Autorización OAuth2

Este microservicio `msvc-oauth` implementa un **Servidor de Autorización OAuth2** utilizando **Spring Authorization Server**, **Spring Security** y **Spring Cloud**.  
Gestiona la autenticación, emisión de tokens y validación de usuarios mediante llamadas remotas, e integra trazabilidad distribuida para observabilidad.

---

## 🚀 Tecnologías y Herramientas

- **Spring Boot 3**, **Spring Authorization Server**, **Spring Security**  
- **OAuth2.1**, **OpenID Connect**, **JWT**  
- **WebClient**, **Spring Cloud LoadBalancer**  
- **Micrometer Tracing**, **Zipkin**  
- **BCryptPasswordEncoder**  
- **Docker**, **Maven**

---

## 📦 Funcionalidades Clave

- Servidor de autorización OAuth2 con:
  - Flujos Authorization Code y Refresh Token  
  - Soporte para OpenID Connect 1.0  
  - Generación de tokens JWT con claims personalizados  
- Autenticación remota de usuarios vía WebClient  
- Generación de claves RSA para firma de tokens  
- Trazabilidad distribuida con Zipkin  
- Descubrimiento de servicios con balanceo de carga

---

## 🧪 Cómo Ejecutar

```bash
mvn clean package
docker build -t msvc-oauth .
docker run -p 8001:8001 msvc-oauth
```
---
## 👨‍💻 Autor
Freyder Otalvaro Desarrollador Senior Java | Arquitecto Backend | AWS Learner

