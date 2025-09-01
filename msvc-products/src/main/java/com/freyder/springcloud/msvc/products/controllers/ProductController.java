package com.freyder.springcloud.msvc.products.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.freyder.libs.msvc.commons.entities.Product;
import com.freyder.springcloud.msvc.products.services.ProductService;

@RestController
// @RequestMapping("/api/products")
public class ProductController {


    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> list(@RequestHeader(name = "message-request", required = false) String message) {
        logger.info("Ingresando al metodo del controller ProductController::list");
        logger.info("message: {}", message);

        return this.service.findAll();
    }
    // 👆 V2 otra manera de implementar el list a través de ResponseEntity:
    // @GetMapping
    // public ResponseEntity<List<Product>> list2(){
    // return ResponseEntity.ok(this.service.findAll());
    // }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException {
        logger.info("Ingresando al metodo del controller ProductController::details");


        // simulando un error, que luego será validado con Resilience4j:
        if (id.equals(10L)) {
            // throw new IllegalStateException("Error al obtener el producto");
        }
        // otra simulación de error con un timeOut:
        if (id.equals(7L)) {
            // si mas mas tiempo de la cuenta sobre 4 segundo debe lanzar una falla
            TimeUnit.SECONDS.sleep(3L);
        }

        Optional<Product> productOptional = service.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    // 🤓Recordar @RequestBody: toma el objeto "Product" y lo transforma en un
    // objeto
    // toma el Json y lo convierte en un objeto "product"
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        logger.info("Ingresando al metodo ProductController::create, creando:{}", product);

        Product productNew = service.save(product);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productNew);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Product product) {
        logger.info("Ingresando al metodo ProductController::update, actualizando:{}", product);

        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            Product productDb = productOptional.orElseThrow();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setCreateAt(product.getCreateAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(productDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            this.service.deleteById(id);
            logger.info("Ingresando al metodo ProductController::delete, eliminado:{}", productOptional.get());

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
