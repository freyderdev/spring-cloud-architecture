package com.freyder.cloud.msvc.items.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;

import com.freyder.cloud.msvc.items.models.Item;
import com.freyder.cloud.msvc.items.services.ItemService;
import com.freyder.libs.msvc.commons.entities.Product;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RefreshScope
@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService service;

    // private final CircuitBreakerFactory cBreakerFactory;
    //solution copylot:⬇
    private final CircuitBreakerFactory<?, ?> cBreakerFactory;

    @Value("${configuration.texto}")
    private String text;

    // tenemos que inyectar un objeto que represetan el environment
    @Autowired
    private Environment env;

    public ItemController(@Qualifier("itemServiceWebClientImpl") ItemService service,
    // public ItemController(@Qualifier("itemServiceFeignImp") ItemService service,
            CircuitBreakerFactory<?, ?> cBreakerFactory) {
        this.service = service;
        this.cBreakerFactory = cBreakerFactory;
    }

    @GetMapping("/fetch-configs")
    public ResponseEntity<?> fetchConfigs(@Value("${server.port}") String port) {
        Map<String, String> json = new HashMap<>();
        json.put("text", text);
        json.put("port", port);
        logger.info(text);
        logger.info(port);

        if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
            json.put("autor.nombre", env.getProperty("configuration.autor.nombre"));
            json.put("autor.email", env.getProperty("configuration.autor.email"));
        }
        return ResponseEntity.ok(json);
    }

    @GetMapping
    public List<Item> list(@RequestParam(name = "name", required = false) String name,
            @RequestHeader(name = "token-request", required = false) String token) {

        logger.info("llamada a metdo del controller ItemController::list()");
        logger.info("Request Parameter: {}", name);
        logger.info("Token: {}", token);

        System.out.println("name = " + name);
        System.out.println("token = " + token);

        return service.findAll();
    }

    @GetMapping("/details1/{id}")
    public ResponseEntity<?> details1(@PathVariable Long id) {
        // Optional<Item> itemOptional = service.findById(id);
        Optional<Item> itemOptional = cBreakerFactory.create("items")
                /*
                 * .run() va intentar ejecutar este método remoto al API Productos
                 * - si todo sale bien devuelve el OPtional con los Items
                 * - si hay un error, se abre el circuito se ejecuta el método de fallback que
                 * emite un error (e) -> para devuelver un Item de prueba
                 */
                .run(() -> service.findById(id), e -> {
                    System.out.println(e.getMessage());
                    logger.error(e.getMessage());

                    Product product = new Product();
                    product.setCreateAt(LocalDate.now());
                    product.setId(1L);
                    product.setName("Camera Sony");
                    product.setPrice(500.00);
                    return Optional.of(new Item(product, 5));
                });

        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections
                        .singletonMap(
                                "message",
                                "The product does not exist in the msvc-products microservice"));
    }

    // esta es una manera mas declarativa con la anotacion @CircuitBreaker
    // es de destacar que la anotación @CircuitBreaker solo funcionará de acuerdo al
    // .yml
    @CircuitBreaker(name = "items")
    @GetMapping("/details2/{id}")
    public ResponseEntity<?> details2(@PathVariable Long id) {
        // Optional<Item> itemOptional = service.findById(id);
        Optional<Item> itemOptional = service.findById(id);

        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(404)
                .body(Collections
                        .singletonMap(
                                "message",
                                "The product does not exist in the msvc-products microservice"));
    }

    // este es el método de fallback "camino alternativo"que se ejecutará si el
    // circuito se abre
    public ResponseEntity<?> getFallBackMethodProduct2(Throwable e) {
        System.out.println(e.getMessage());
        logger.error(e.getMessage());

        Product product = new Product();
        product.setCreateAt(LocalDate.now());
        product.setId(1L);
        product.setName("Camera Sony");
        product.setPrice(500.00);
        return ResponseEntity.ok(new Item(product, 5));
    }

    // En este método se invoca una llamada en el futuro, que tiene cierto delay
    @CircuitBreaker(name = "items", fallbackMethod = "getFallBackMethodProduct3")
    @TimeLimiter(name = "items")
    @GetMapping("/details3/{id}")
    public CompletableFuture<?> details3(@PathVariable Long id) {
        // Optional<Item> itemOptional = service.findById(id);
        return CompletableFuture.supplyAsync(() -> {

            Optional<Item> itemOptional = service.findById(id);

            if (itemOptional.isPresent()) {
                return ResponseEntity.ok(itemOptional.get());
            }
            return ResponseEntity.status(404)
                    .body(Collections
                            .singletonMap(
                                    "message",
                                    "The product does not exist in the msvc-products microservice"));
        });

    }

    // este es el método de fallback "camino alternativo"que se ejecutará si el
    // circuito se abre, para el método details3
    public CompletableFuture<?> getFallBackMethodProduct3(Throwable e) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println(e.getMessage());
            logger.error(e.getMessage());

            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Camera Sony");
            product.setPrice(500.00);
            return ResponseEntity.ok(new Item(product, 5));
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        logger.info("Product creando: {}", product);
        return service.save(product);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id) {
        logger.info("Product actualizando: {}", product);
        
        return service.update(product, id);
    }
    
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        logger.info("Product eliminado con id: {}", id);
        service.delete(id);
    }

}
