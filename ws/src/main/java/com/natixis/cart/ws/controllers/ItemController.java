package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    ItemService itemService;


    @PutMapping("/items")
    public ResponseEntity<Product> updateItem(@RequestBody Product product){
        Product newProduct = itemService.update(product);
        return ResponseEntity.ok().body(newProduct);
    }

    @PostMapping("/items")
    public ResponseEntity<Product> createItem(@RequestBody Product product){
        final Product newProduct = itemService.create(product);
        return ResponseEntity.ok().body(newProduct);
    }
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/items/{itemId}")
    public ResponseEntity<Product> findById(@PathVariable String itemId){
        try {
            final Product newProduct = itemService.findById(itemId);
            return ResponseEntity.ok().body(newProduct);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/items")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = itemService.findAll();
        return ResponseEntity.ok().body(products);
    }
}
