package com.freyder.springcloud.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.freyder.libs.msvc.commons.entities.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {

}
