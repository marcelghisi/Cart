package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    ProductService productService;


    @PutMapping("/products")
    public ResponseEntity<Product> updateItem(@RequestBody Product product){
        Product newProduct = productService.update(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createItem(@RequestBody Product product){
        final Product newProduct = productService.create(product);
        return ResponseEntity.ok().body(newProduct);
    }
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> findById(@PathVariable String productId){
        try {
            final Product newProduct = productService.findById(productId);
            return ResponseEntity.ok().body(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok().body(products);
    }
}
