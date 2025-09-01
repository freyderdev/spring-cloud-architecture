package com.freyder.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;

import com.freyder.libs.msvc.commons.entities.Product;


public interface ProductService {

    List<Product> findAll();
    /*
     * Optional<Product> findById(Long id);
     * Hay que recordar el Optional representa 1 solo objeto, podríamos obtener el Producto,
     * pero nos permite validar un poco mas, si el objeto se encontro o no. Podemos hacer algo para ejecutar
     * los métodos en caso que este presente y si no manejar algun tipo de error. para evitar un null pointer
     * envolvemos el resultado de la consulta en un Optional.
     */
    Optional<Product> findById(Long id);

    Product save(Product product);

    void deleteById(Long id);
}
