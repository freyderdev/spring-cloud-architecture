
# 🧩 Microservicio Items - Cliente de Productos

Este microservicio `msvc-items` es un cliente que se comunica con el microservicio **msvc-products**.  
Está desarrollado con **Spring Boot 3**, **Spring Cloud** y utiliza **WebClient** y **Feign Client** para la comunicación entre servicios.  
Incluye tolerancia a fallos mediante **Resilience4J**, balanceo de carga con **LoadBalancer** y trazabilidad distribuida con **Micrometer Tracing** y **Zipkin**.

---

## 🚀 Tecnologías y Herramientas

- **Spring Boot 3**, **Spring WebFlux**, **Spring Cloud OpenFeign**  
- **Resilience4J** para Circuit Breaker  
- **WebClient** para llamadas HTTP reactivas  
- **Feign Client** para comunicación declarativa  
- **Micrometer Tracing** y **Zipkin** para trazabilidad  
- **Docker** para contenerización  
- **Maven** como gestor de dependencias

---

## 🧩 Funcionalidades

- Comunicación con `msvc-products` mediante:
  - **Feign Client**
  - **WebClient** con balanceo de carga
- Gestión de Items (producto + cantidad):
  - Listar todos los Items
  - Obtener detalles de un Item por ID
  - Crear, actualizar y eliminar productos
- Tolerancia a fallos:
  - Circuit Breaker con Resilience4J
  - Fallbacks para métodos que puedan fallar
- Configuración centralizada:
  - Lectura de propiedades desde Spring Cloud Config
  - Refresco dinámico de configuración (`@RefreshScope`)
- Soporte para llamadas asíncronas con `CompletableFuture`

---

## 🧱 Arquitectura

msvc-items (cliente) --> msvc-products (producto)
│
├─ WebClient (reactivo, balanceado)
└─ Feign Client (declarativo)

---

- `ItemServiceWebClientImpl` → Implementación con **WebClient**
- `ItemServiceFeignImp` → Implementación con **Feign Client**
- `ItemController` → Expone endpoints REST para el consumo de Items

---

## 📦 Endpoints principales

| Método | Ruta | Descripción |
|--------|------|------------|
| GET | / | Lista todos los Items |
| GET | /details1/{id} | Obtiene detalle de Item con fallback manual |
| GET | /details2/{id} | Obtiene detalle con anotación @CircuitBreaker |
| GET | /details3/{id} | Obtiene detalle de forma asíncrona con fallback |
| POST | / | Crear un producto |
| PUT | /{id} | Actualizar un producto |
| DELETE | /{id} | Eliminar un producto |
| GET | /fetch-configs | Devuelve configuración actual y perfil activo |

---

## 🛠 Configuración y ejecución

1. Asegúrate de tener **Docker** y **Maven** instalados.
2. Construye el proyecto:
```bash
  mvn clean package

3. Construye el proyecto:
docker build -t msvc-items .

4. Ejecuta el contenedor:
docker run -p 8005:8005 msvc-items

Nota: Ajusta la variable config.baseurl.endpoint.msvc-products en application.yml o Config Server según la URL del microservicio msvc-products.

---

🔧 Dependencias clave
	• Spring Boot Starter Web & WebFlux
	• Spring Cloud OpenFeign
	• Spring Cloud Circuit Breaker Resilience4J
	• Spring Cloud LoadBalancer
	• Micrometer Tracing & Zipkin
  • libs-msvc-commons (entidades compartidas)

---

📌 Observaciones
	• Los Items son un wrapper que combina un Producto y una cantidad aleatoria para demostración.
	• Se puede cambiar entre WebClient o Feign Client modificando la inyección del ItemService.
  • Incluye ejemplos de fallback y manejo de errores para resiliencia en llamadas remotas.

---

🧪 Ejemplo de uso
# Listar todos los Items
curl http://localhost:8005/

# Obtener detalle de un Item
curl http://localhost:8005/details2/1

# Crear un producto
curl -X POST http://localhost:8005/ -H "Content-Type: application/json" -d '{"name":"Camera Canon","price":700.0}'

# Actualizar un producto
curl -X PUT http://localhost:8005/1 -H "Content-Type: application/json" -d '{"name":"Camera Nikon","price":650.0}'

# Eliminar un producto
curl -X DELETE http://localhost:8005/1

---
📦 Docker
	• Puerto expuesto: 8005
	• Imagen: msvc-items
	• EntryPoint: java -jar msvc-items.jar




