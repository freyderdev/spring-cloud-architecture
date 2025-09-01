package com.freyder.cloud.msvc.items.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClient.Builder;
// import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.freyder.cloud.msvc.items.models.Item;
import com.freyder.libs.msvc.commons.entities.Product;

// @Primary
@Service
public class ItemServiceWebClientImpl implements ItemService {

    // private final WebClient.Builder client;
    //ya se trabaja con el @ Bean webClient y no con el Builder, ya el webClient viene con todo  loadBalancer
    // y con todo el contexto propagado
    private final WebClient client;

    public ItemServiceWebClientImpl(WebClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {

        return this.client
                .get()
                // .uri("http://msvc-products")
                .accept(MediaType.APPLICATION_JSON)
                /*
                 * .retrieve: Este método envía la solicitud al servidor y espera la respuesta.
                 * Es el punto en el que la solicitud HTTP se realiza efectivamente
                 */
                .retrieve()
                // .bodyToFlux: La respuesta se convierte en un flujo (Flux) de objetos Product.
                .bodyToFlux(Product.class)
                // Transformación de los productos en items con el .map()
                // Cada producto se transforma en un objeto Item, asignándole una cantidad
                // aleatoria entre 1 y 10.
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .collectList()
                .block();
    }

    @Override
    public Optional<Item> findById(Long id) {
        // tenemos que pasar parametros:
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        // try {
        return Optional.of(client.get().uri("/{id}", params)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Product.class)
                .map(product -> new Item(product, new Random().nextInt(10) + 1))
                .block());

        // } catch (WebClientResponseException e) {
        // return Optional.empty();
        // }

    }

    /*
     * El método save() es responsable de enviar una
     * solicitud HTTP POST para guardar un objeto Product en otro servicio
     * (posiblemente un microservicio de productos).
     */
    @Override
    public Product save(Product product) {
        return client
                .post() // 1️⃣ Configura la solicitud HTTP como POST
                .contentType(MediaType.APPLICATION_JSON) // 2️⃣ Especifica que enviará JSON
                .bodyValue(product) // 3️⃣ Adjunta el objeto product como el cuerpo de la solicitud
                .retrieve() // 4️⃣ Envía la solicitud y espera una respuesta
                // 5️⃣ bodyToMono: Convierte la respuesta en un objeto Product de forma reactiva
                .bodyToMono(Product.class)
                .block(); // 6️⃣ Bloquea la ejecución hasta recibir la respuesta
    }

    @Override
    public Product update(Product product, Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);
        return client
                .put() // 1️⃣ Configura la solicitud HTTP como PUT
                .uri("/{id}", params) // 2️⃣ Define la URI con el ID del producto
                .accept(MediaType.APPLICATION_JSON) // 3️⃣ Indica que espera una respuesta JSON
                .contentType(MediaType.APPLICATION_JSON) // 4️⃣ Indica que enviará JSON
                .bodyValue(product) // 5️⃣ Adjunta el objeto product como cuerpo de la solicitud
                .retrieve() // 6️⃣ Envía la solicitud y espera la respuesta
                // 7️⃣ bodyToMono: Convierte la respuesta en un objeto Product de forma reactiva
                .bodyToMono(Product.class) 
                .block(); // 8️⃣ Bloquea la ejecución hasta recibir la respuesta
    }

    @Override
    public void delete(Long id) {
        Map<String, Long> params = new HashMap<>();
        params.put("id", id);

        client.delete().uri("/{id}", params)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

    }

}
