
# 🔐 OAuth2 Authorization Microservice

This microservice `msvc-oauth` implements an **OAuth2 Authorization Server** using **Spring Authorization Server**, **Spring Security**, and **Spring Cloud**.  
It handles authentication, token issuance, and user validation via remote service calls, and integrates distributed tracing for observability.

---

## 🚀 Technologies and Tools

- **Spring Boot 3**, **Spring Authorization Server**, **Spring Security**
- **OAuth2.1**, **OpenID Connect**, **JWT**
- **WebClient**, **Spring Cloud LoadBalancer**
- **Micrometer Tracing**, **Zipkin**
- **BCryptPasswordEncoder**
- **Docker**, **Maven**

---

## 📦 Key Features

- OAuth2 Authorization Server with:
  - Authorization Code and Refresh Token grants
  - OpenID Connect 1.0 support
  - JWT token generation with custom claims
- Remote user authentication via WebClient
- RSA key pair generation for secure token signing
- Distributed tracing with Zipkin
- Load-balanced service discovery for user validation

---

## 🧪 How to Run

### `Role.java`
```bash
mvn clean package
docker build -t msvc-oauth .
docker run -p 8001:8001 msvc-oauth
```
## 👨‍💻 Author
Freyder Otalvaro Senior Java Developer | AWS Learner GitHub
