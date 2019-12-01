package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;


    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User newUser = userService.update(user);
        return ResponseEntity.ok().body(newUser);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        User newUser = userService.create(user);
        return ResponseEntity.ok().body(newUser);
    }
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.delete(userId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> findById(@PathVariable String userId){
        User newUser = null;
        try {
            newUser = userService.findById(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(newUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }
}
