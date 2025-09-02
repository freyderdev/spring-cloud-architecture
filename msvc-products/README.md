📦 Microservicio: Products (msvc-products)

Este microservicio gestiona el ciclo de vida de los productos en la arquitectura distribuida. Forma parte del ecosistema de microservicios interconectados mediante Eureka Server y expuestos a través del Gateway.

🚀 Tecnologías

Spring Boot 3, Spring Data JPA, Hibernate

Spring Cloud Netflix Eureka Client

MySQL 8 (persistencia)

Micrometer Tracing + Zipkin (trazabilidad distribuida)

Docker (contenedorización)

⚙️ Funcionalidades principales

CRUD completo de productos:

Crear producto

Listar todos los productos

Obtener producto por ID

Actualizar producto

Eliminar producto

Integración con Eureka Server para registro dinámico.

Persistencia con MySQL utilizando JPA/Hibernate.

Simulación de fallos y timeouts para validar tolerancia a fallos (ej. Resilience4J desde clientes).

Trazabilidad distribuida con Zipkin para requests entre microservicios.

📡 Endpoints principales
Método	Endpoint	Descripción
GET	/	Lista todos los productos
GET	/{id}	Obtiene un producto por ID
POST	/	Crea un nuevo producto
PUT	/{id}	Actualiza un producto
DELETE	/{id}	Elimina un producto
🗄️ Entidad principal
public class Product {
    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;
}

🐳 Docker

Construir la imagen del microservicio:

mvn clean package -DskipTests
docker build -t msvc-products .


Ejecutar el contenedor:

docker run -p 8001:8001 msvc-products

🔗 Integraciones

Eureka Server: registro y descubrimiento de instancias.

Gateway Server: seguridad y entrada unificada al sistema.

Commons Library (libs-msvc-commons): reutilización de la entidad Product.

Zipkin: trazabilidad distribuida de peticiones.

👨‍💻 Autor: Freyder Otálvaro
Senior Java Developer | Backend Architect | AWS Learner
