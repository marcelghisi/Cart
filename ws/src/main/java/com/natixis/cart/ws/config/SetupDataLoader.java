package com.natixis.cart.ws.config;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

@Configuration
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ProductRepository productRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        createProductIfNotFound(Product.builder().name("T-Shirt Blue 1").price(10.0).build());
        createProductIfNotFound(Product.builder().name("T-Shirt Red 2").price(20.0).build());
        createProductIfNotFound(Product.builder().name("T-Shirt Red 3").price(25.0).build());
    }

    private void createProductIfNotFound(Product product){
        List<Product> existingProducts = this.productRepository.findByName(product.getName());
        if (existingProducts.stream().count() == 0){
            this.productRepository.save(product);
        }
    }

}
