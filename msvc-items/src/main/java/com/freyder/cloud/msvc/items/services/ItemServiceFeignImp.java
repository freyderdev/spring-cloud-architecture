package com.freyder.cloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freyder.cloud.msvc.items.clients.ProductFeignClient;
import com.freyder.cloud.msvc.items.models.Item;
import com.freyder.libs.msvc.commons.entities.Product;

import feign.FeignException;

@Service
public class ItemServiceFeignImp implements ItemService {

    @Autowired
    private ProductFeignClient client;

    /*
     * el client.findAll() devuelve una lista de productos, y se necesita convertir
     * esa lista de productos a una lista de items
     * el resultado final es una lista de items que contengan el precio y la
     * cantidad de cada producto.
     * se puede utilizar el API de stream de java 8 para hacer esto, ya que nos
     * permite modificar el flujo.
     * 1. se obtiene la lista de productos
     * 2. se convierte la lista de productos a un stream, esta conversion es a
     * través del metodo .map() el cual nos
     * permite modificar el flujo de datos para que nos devuelva una colecion de
     * otro tipo de objetos como es la lista de items.
     * es decir cambiar un List<Product> a un List<Item> con datos adicionales
     * - en el Map se emite cada producto, y es una expresion lambda que recibe un
     * producto y retorna un item.
     * es decir por cada producto vamos a devolver el item que contiene el producto
     * y una cantidad aleatoria
     * 🔸collect es un metodo terminal que nos permite colectar todos los elementos
     * que tenemos en el .map()
     * y llevarlos a un tipo List
     * 3. se mapea cada producto a un item, y se le asigna una cantidad aleatoria
     * 4. se colecta el stream en una lista
     * 5. se retorna la lista de items
     * 
     */

    @Override
    public List<Item> findAll() {
        return client.findAll().stream().map(product -> {
            Random random = new Random();
            return new Item(product, random.nextInt(10) + 1);
        }).collect(Collectors.toList());
    }
    // //👆v2:
    // public List<Item> findAllV2() {
    // return client.findAll()
    // .stream()
    // .map(product -> new Item(product, new Random().nextInt(10) + 1))
    // .collect(Collectors.toList());
    // }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            return Optional.empty();
        }
    }

    @Override
    public Product save(Product product) {
        return client.create(product);
    }

    @Override
    public Product update(Product product, Long id) {
        return client.update(product, id);
    }

    @Override
    public void delete(Long id) {
        client.delete(id);
    }
}
