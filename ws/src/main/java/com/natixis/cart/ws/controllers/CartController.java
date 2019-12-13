package com.natixis.cart.ws.controllers;

import com.natixis.cart.ws.HistoryResponse;
import com.natixis.cart.ws.UserResponse;
import com.natixis.cart.ws.domain.Cart;
import com.natixis.cart.ws.domain.Item;
import com.natixis.cart.ws.domain.PurchaseCartHistory;
import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.exception.RuleException;
import com.natixis.cart.ws.exception.UserServiceException;
import com.natixis.cart.ws.rules.CartRules;
import com.natixis.cart.ws.services.PurchaseCartHistoryService;
import com.natixis.cart.ws.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @Autowired
    PurchaseCartHistoryService purchaseCartHistoryService;

    @PutMapping("/users/{userId}/cart")
    public ResponseEntity<UserResponse> updateItemToUserCart(@PathVariable String userId, @RequestBody Item cartItem){
        try {
            cartRules.validatePrice(cartItem);
            User user = userService.findById(userId);
            Optional<Cart> cart = Optional.ofNullable(user.getCart());
            if (!cart.isPresent()){
                return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList("Item not found")).status("ERROR").build());
            } else {
                cart.get().setTotalValue(cart.get().getItems().stream().mapToDouble(item->item.getQuantity()*item.getProduct().getPrice()).sum());
                cart.get().getItems().stream().filter(shopCart -> shopCart.getProduct().getId().equals(cartItem.getProduct().getId())).forEach(cartToUpdate -> {
                    if (!cartToUpdate.getQuantity().equals(cartItem.getQuantity())){
                        cartToUpdate.setQuantity(cartItem.getQuantity());
                    }
                });
                user.setCart(cart.get());
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
    public ResponseEntity<UserResponse> addItemToUserCart(@PathVariable String userId, @RequestBody Item cartItem){
        try {
            User user = userService.findById(userId);
            cartRules.validatePrice(cartItem);
            Optional<Cart> cart = Optional.ofNullable(user.getCart());
            if (cart.isPresent()){
                if (cart.get().getItems().stream().filter(item->item.getProduct().getId().equals(cartItem.getProduct().getId())).count() > 0){
                    UserResponse userResponse = UserResponse.builder().status("FAIL").errors(Arrays.asList("Item has already been added")).build();
                    return ResponseEntity.ok(userResponse);
                } else {
                    cart.get().getItems().add(cartItem);
                    cart.get().setTotalValue(cart.get().getItems().stream().mapToDouble(item->item.getQuantity()*item.getProduct().getPrice()).sum());
                    User newUser = userService.update(User.builder().id(user.getId()).password(user.getPassword()).email(user.getEmail()).firstName(user.getFirstName()).cart(cart.get()).build());
                    UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCESS").build();
                    return ResponseEntity.ok(userResponse);
                }
            } else {
                Cart newCart = Cart.builder().items(Arrays.asList(cartItem)).totalValue(cartItem.getQuantity()*cartItem.getProduct().getPrice()).build();
                User newUser = userService.update(User.builder().id(user.getId()).password(user.getPassword()).email(user.getEmail()).firstName(user.getFirstName()).cart(newCart).build());
                UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCESS").build();
                return ResponseEntity.ok(userResponse);
            }
        } catch (RuleException e) {
            log.error(e.getMessage());
            return ResponseEntity.unprocessableEntity().body(UserResponse.builder().errors(Arrays.asList(e.getMessage())).status("ERROR").build());
        } catch (Exception e) {
            log.error(e.getMessage());
            UserResponse userResponse = UserResponse.builder().status("FAIL").errors(Arrays.asList("Internal error. Please contact Administrator")).build();
            return ResponseEntity.ok(userResponse);
        }
    }

    @DeleteMapping("/users/{userId}/cart/{productId}")
    public ResponseEntity<UserResponse> excludeItemFromUserCart(@PathVariable String userId, @PathVariable String productId){
        try {
            User user = userService.findById(userId);
            Optional<Cart> cart = Optional.ofNullable(user.getCart());
            if (cart.isPresent() && cart.get().getItems().stream().filter(item->item.getProduct().getId().equals(productId)).count() == 1){
                cart.get().getItems().removeIf(item -> item.getProduct().getId().equals(productId));
                cart.get().setTotalValue(cart.get().getItems().stream().mapToDouble(item->item.getQuantity()*item.getProduct().getPrice()).sum());
                User newUser = userService.update(user);
                UserResponse userResponse = UserResponse.builder().data(newUser).status("SUCCESS").build();
                return ResponseEntity.ok(userResponse);
            }else{
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
            Optional<Cart> cart = Optional.ofNullable(user.getCart());
            if (cart.isPresent()){
                Comparator<Item> compareItemName  = (i1, i2)-> i1.getProduct().getName().compareTo(i2.getProduct().getName());
                Comparator<Item> compareItemPrice = (p1, p2)-> p1.getProduct().getPrice().compareTo(p2.getProduct().getPrice());
                Comparator<Item> compare  = compareItemName.thenComparing(compareItemPrice);

                List<Item> sortedList = cart.get().getItems().stream()
                        .sorted(compare)
                        .collect(Collectors.toList());
                cart.get().setItems(sortedList);
                user.setCart(cart.get());
            } else {
                return ResponseEntity.noContent().build();
            }
           return ResponseEntity.ok().body(UserResponse.builder().data(user).status("SUCCESS").build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.ok().body(UserResponse.builder().errors(Arrays.asList("Internal error")).status("FAIL").build());
        }
    }

    @PostMapping("/users/{userId}/cart/checkout")
    public ResponseEntity<UserResponse> finalizeCheckout(@PathVariable String userId){
        try {
            Optional<User> user = Optional.ofNullable(userService.findById(userId));
            if (user.isPresent()) {
                PurchaseCartHistory purchaseCartHistory = PurchaseCartHistory.builder()
                        .purchaseDate(new Date())
                        .status("PENDING")
                        .user(user.get())
                        .build();
                Optional<PurchaseCartHistory> purchaseCartSaved = Optional.ofNullable(purchaseCartHistoryService.create(purchaseCartHistory));
                if (purchaseCartSaved.isPresent()) {
                    user.get().setCart(null);
                    User newUser = userService.update(user.get());
                    return ResponseEntity.ok().body(UserResponse.builder().data(newUser).status("SUCCESS").build());
                }
            }
        } catch (UserServiceException e) {
            return ResponseEntity.ok().body(UserResponse.builder().errors(Arrays.asList(e.getMessage())).status("FAIL").build());
        } catch (Exception e) {
            log.error("finalizeCheckout Error",e.getMessage());
            return ResponseEntity.ok().body(UserResponse.builder().errors(Arrays.asList("Internal server error",e.getMessage())).status("FAIL").build());
        }
        return ResponseEntity.ok().body(UserResponse.builder().errors(Arrays.asList("User not found")).status("FAIL").build());
    }

    @GetMapping("/users/{userId}/cart/history")
    public ResponseEntity<HistoryResponse> listHistory(@PathVariable String userId){
        List<PurchaseCartHistory> histories = purchaseCartHistoryService.findByUserId(userId);
        return ResponseEntity.ok().body(HistoryResponse.builder().status("SUCCESS").data(histories).build());
    }


}
