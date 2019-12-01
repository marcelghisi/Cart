package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.domain.Item;
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
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        Item newItem = itemService.update(item);
        return ResponseEntity.ok().body(newItem);
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@RequestBody Item user){
        Item newItem = itemService.create(user);
        return ResponseEntity.ok().body(newItem);
    }
    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable String itemId) {
        itemService.delete(itemId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/items/{itemId}")
    public ResponseEntity<Item> findById(@PathVariable String itemId){
        Item newItem = null;
        try {
            newItem = itemService.findById(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(newItem);
    }

    @GetMapping("/items")
    public ResponseEntity<List<Item>> findAll() {
        List<Item> items = itemService.findAll();
        return ResponseEntity.ok().body(items);
    }
}
