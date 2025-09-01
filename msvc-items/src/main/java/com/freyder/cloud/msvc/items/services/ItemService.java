package com.freyder.cloud.msvc.items.services;

import java.util.List;
import java.util.Optional;

import com.freyder.cloud.msvc.items.models.Item;
import com.freyder.libs.msvc.commons.entities.Product;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);
    
    void delete(Long id);

}
