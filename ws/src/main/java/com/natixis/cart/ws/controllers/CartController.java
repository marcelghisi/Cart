package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.UserResponse;
import com.natixis.cart.ws.domain.Cart;
import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.exception.RuleException;
import com.natixis.cart.ws.rules.CartRules;
import com.natixis.cart.ws.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Log4j2
public class CartController {

    @Autowired
    UserService userService;

    @Autowired
    CartRules cartRules;

    @PutMapping("/users/{userId}/cart")
    public ResponseEntity<UserResponse> updateItemToUserCart(@PathVariable String userId, @RequestBody Cart cartItem){
        try {
            cartRules.validatePrice(cartItem);
            User user = userService.findById(userId);
            List<Cart> cart = user.getCart();
            if (CollectionUtils.isEmpty(cart)){
                return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList("Item not found")).status("ERROR").build());
            } else {
                cart.stream().filter(shopCart -> shopCart.getItem().getId().equals(cartItem.getItem().getId())).forEach(cartToUpdate -> {
                    if (!cartToUpdate.getQuantidade().equals(cartItem.getQuantidade())){
                        cartToUpdate.setQuantidade(cartItem.getQuantidade());
                    }
                });
                user.setCart(cart);
                User newUser = userService.update(user);
                return ResponseEntity.ok().body(UserResponse.builder().data(newUser).status("SUCCESS").build());
            }
        } catch (RuleException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList(e.getMessage())).status("ERROR").build());
        }catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList("System unavailable")).status("ERROR").build());
        }

    }

    @PostMapping("/users/{userId}/cart")
    public ResponseEntity<UserResponse> addItemToUserCart(@PathVariable String userId, @RequestBody Cart cartItem){
        try {
            User user = userService.findById(userId);
            cartRules.validatePrice(cartItem);
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
        } catch (RuleException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList(e.getMessage())).status("ERROR").build());
        } catch (Exception e) {
            log.error(e.getMessage());
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
            log.error(e.getMessage());
            UserResponse userResponse = UserResponse.builder().errors(Arrays.asList("Internal error. Please contact Administrator")).build();
            return ResponseEntity.ok(userResponse);
        }
    }

    @GetMapping("/users/{userId}/cart/resume")
    public ResponseEntity<UserResponse> resumecart(@PathVariable String  userId){
        try {
        User user = userService.findById(userId);
        List<Cart> cart = user.getCart();
            Comparator<Cart> compareItemName  = (i1, i2)-> i1.getItem().getName().compareTo(i2.getItem().getName());
            Comparator<Cart> compareItemPrice = (p1, p2)-> p1.getItem().getPrice().compareTo(p2.getItem().getPrice());
            Comparator<Cart> compare  = compareItemName.thenComparing(compareItemPrice);

            List<Cart> sortedList = cart.stream()
                .sorted(compare)
                .collect(Collectors.toList());

            user.setCart(sortedList);
           return ResponseEntity.ok().body(UserResponse.builder().data(user).status("SUCCESS").build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok().body(UserResponse.builder().errors(Arrays.asList("Internal error")).status("FAIL").build());
        }
    }
}
