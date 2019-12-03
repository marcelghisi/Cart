package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.UserResponse;
import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService userService;


    @PutMapping("/users")
    public ResponseEntity<UserResponse>  updateUser(@RequestBody User user){
        User newUser = userService.update(user);
        UserResponse userResponse = UserResponse.builder().status("SUCCESS").data(newUser).build();
        return ResponseEntity.ok().body(userResponse);
    }

    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) throws Exception {
        Optional<List<User>>  usersFound = userService.findByEmail(user.getEmail());
        if (usersFound.isPresent()){
            UserResponse userResponse = UserResponse.builder().status("FAIL").errors(Arrays.asList("User already registered")).build();
            return ResponseEntity.ok().body(userResponse);
        } else {
            User newUser = userService.create(user);
            UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCCESS").build();
            return ResponseEntity.ok().body(userResponse);
        }

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
