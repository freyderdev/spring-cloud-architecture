# 🧩 Items Microservice - Products Client

This microservice `msvc-items` is a client that communicates with the **msvc-products** microservice.  
It is built with **Spring Boot 3**, **Spring Cloud**, and uses **WebClient** and **Feign Client** for inter-service communication.  
It includes fault tolerance with **Resilience4J**, load balancing with **LoadBalancer**, and distributed tracing with **Micrometer Tracing** and **Zipkin**.

---

## 🚀 Technologies and Tools

- **Spring Boot 3**, **Spring WebFlux**, **Spring Cloud OpenFeign**  
- **Resilience4J** for Circuit Breaker  
- **WebClient** for reactive HTTP calls  
- **Feign Client** for declarative communication  
- **Micrometer Tracing** and **Zipkin** for distributed tracing  
- **Docker** for containerization  
- **Maven** as dependency manager

---

## 🧩 Features

- Communication with `msvc-products` via:
  - **Feign Client**
  - **WebClient** with load balancing
- Items management (product + quantity):
  - List all Items
  - Get Item details by ID
  - Create, update, and delete products
- Fault tolerance:
  - Circuit Breaker with Resilience4J
  - Fallbacks for potentially failing methods
- Centralized configuration:
  - Properties loaded from Spring Cloud Config
  - Dynamic configuration refresh (`@RefreshScope`)
- Support for asynchronous calls with `CompletableFuture`

---

## 🧱 Architecture
msvc-items (client) --> msvc-products (product)
│
├─ WebClient (reactive, load balanced)
└─ Feign Client (declarative)

---

- `ItemServiceWebClientImpl` → WebClient implementation  
- `ItemServiceFeignImp` → Feign Client implementation  
- `ItemController` → Exposes REST endpoints for consuming Items  

---

## 📦 Main Endpoints

| Method | Path | Description |
|--------|------|------------|
| GET | / | List all Items |
| GET | /details1/{id} | Get Item details with manual fallback |
| GET | /details2/{id} | Get Item details with @CircuitBreaker annotation |
| GET | /details3/{id} | Get Item details asynchronously with fallback |
| POST | / | Create a product |
| PUT | /{id} | Update a product |
| DELETE | /{id} | Delete a product |
| GET | /fetch-configs | Returns current configuration and active profile |

---

## 🛠 Setup and Run

1. Make sure **Docker** and **Maven** are installed.
2. Build the project:
```bash
mvn clean package
```
3. Build the Docker image:
```bash
docker build -t msvc-items .
```
4. Run the container:
```bash
docker run -p 8005:8005 msvc-items
```
---
Note: Adjust the variable config.baseurl.endpoint.msvc-products in application.yml or Config Server according to the URL of the msvc-products microservice.
---
## 🔧 Key Dependencies
- Spring Boot Starter Web & WebFlux
- Spring Cloud OpenFeign
- Spring Cloud Circuit Breaker Resilience4J
- Spring Cloud LoadBalancer
- Micrometer Tracing & Zipkin
- libs-msvc-commons (shared entities)
---
## 📌 Notes
- Items are a wrapper combining a Product and a random quantity for demonstration purposes.
- You can switch between WebClient or Feign Client by modifying the injected ItemService.
- Includes fallback examples and error handling for resilience in remote calls.
---
## 🧪 Usage Examples
### List all Items
curl http://localhost:8005/
### Get Item details
curl http://localhost:8005/details2/1
### Create a product
curl -X POST http://localhost:8005/ -H "Content-Type: application/json" -d '{"name":"Camera Canon","price":700.0}'
### Update a product
curl -X PUT http://localhost:8005/1 -H "Content-Type: application/json" -d '{"name":"Camera Nikon","price":650.0}'
### Delete a product
curl -X DELETE http://localhost:8005/1

---
## 📦 Docker

- Exposed port: 8005
- Image: msvc-items
- EntryPoint: java -jar msvc-items.jar



