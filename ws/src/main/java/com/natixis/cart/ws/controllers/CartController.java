package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.UserResponse;
import com.natixis.cart.ws.domain.Cart;
import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.services.CartService;
import com.natixis.cart.ws.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    CartService cartService;

    @Autowired
    UserService userService;

    @PostMapping("/users/{userId}/cart")
    public ResponseEntity<UserResponse> addItemToUserCart(@PathVariable String userId, @RequestBody Cart cartItem){
        try {
            User user = userService.findById(userId);
            if (user.getCart() != null && user.getCart().stream().filter(cart->cart.getItem().getId().equals(cartItem.getItem().getId())).count() > 0){
                UserResponse userResponse = UserResponse.builder().errors(Arrays.asList("Item has already been added")).build();
                return ResponseEntity.ok(userResponse);
            } else {
                List<Cart> cart = user.getCart() == null ? new ArrayList<>(0) : user.getCart();
                cart.add(cartItem);
                User newUser = userService.update(User.builder().id(user.getId()).email(user.getEmail()).firstName(user.getFirstName()).cart(cart).build());
                UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCESS").build();
                return ResponseEntity.ok(userResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UserResponse userResponse = UserResponse.builder().errors(Arrays.asList("Internal error. Please contact Administrator")).build();
            return ResponseEntity.ok(userResponse);
        }
    }

    @DeleteMapping("/users/{userId}/cart/{itemId}")
    public ResponseEntity<UserResponse> excludeItemFromUserCart(@PathVariable String userId, @PathVariable String itemId){
        try {
            User user = userService.findById(userId);
            if (user.getCart() != null && user.getCart().stream().filter(cart->cart.getItem().getId().equals(itemId)).count() == 1){
                user.getCart().removeIf(cart -> cart.getItem().getId().equals(itemId));
                User newUser = userService.update(user);
                UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCCESS").build();
                return ResponseEntity.ok(userResponse);
            } else {
                UserResponse userResponse = UserResponse.builder().status("FAIL").errors(Arrays.asList("Not Found")).build();
                return ResponseEntity.ok(userResponse);
            }
        } catch (Exception e) {
            e.printStackTrace();
            UserResponse userResponse = UserResponse.builder().errors(Arrays.asList("Internal error. Please contact Administrator")).build();
            return ResponseEntity.ok(userResponse);
        }
    }
}
