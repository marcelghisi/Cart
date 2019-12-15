package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.exception.ProductServiceException;
import com.natixis.cart.ws.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public Product create(final Product product) {
        return productRepository.save(product);
    }

    public Product findById(String productId) throws ProductServiceException {
        final Optional<Product> item = productRepository.findById(productId);
        return item.orElseThrow(()-> new ProductServiceException("Product not found"));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public void delete(final String itemId) {
        productRepository.deleteById(itemId);
    }

    public Product update(final Product product) {
        return productRepository.save(product);
    }
}
