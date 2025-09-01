package com.freyder.springcloud.msvc.products.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freyder.libs.msvc.commons.entities.Product;
import com.freyder.springcloud.msvc.products.repositories.ProductRepository;

@Service
public class ProducServiceImpl implements ProductService {

    // @Autowired
    // private ProductRepository repository;

    // practica de inyección de dependencias recomendada":
    final private ProductRepository repository;

    final private Environment environment;

    public ProducServiceImpl(ProductRepository repository, Environment environment) {
        this.repository = repository;
        this.environment = environment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        /*
         * se pone entre parentesis ((List<Product>)repository.findAll()) de manera que
         * podamos hacer un cast.
         * y ahi si podemos obtener el "stream() y se llama el .map() para tratar con el
         * flujo:
         * se emite el producto a través de una lamda y tenemos que devolver el producto
         * con el puerto
         * el puerto se obtiene? este se debe de inyectar en el "constructor" a través
         * de un componente de Spring que viene de fabrica que se llama "Environment" y
         * a través de ese
         * atributo podemos obtener el puerto, dicho puerto es seteado al "producto"
         * Ya luego con el Stream modificado se llama al collect y al Collectors.tolist
         * para que
         * retorne nuevamente una lista modificada
         */
        return ((List<Product>) repository.findAll()).stream().map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id).map(product -> {
            product.setPort(Integer.parseInt(environment.getProperty("local.server.port")));
            return product;
        });
    }
    //👆version mejorada: 
    // @Override
    // @Transactional(readOnly = true)
    // public Optional<Product> findById(Long id) {
    //     return repository.findById(id)
    //             .map(product -> {
    //                 int port = Integer.parseInt(environment.getProperty("local.server.port"));
    //                 product.setPort(port);
    //                 return product;
    //             });
    // }

    @Override
    @Transactional
    public Product save(Product product) {
        return this.repository.save(product);
        
    }
    
    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
       
    }
}
