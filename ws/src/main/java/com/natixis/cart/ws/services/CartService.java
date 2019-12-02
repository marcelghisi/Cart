package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.Cart;
import com.natixis.cart.ws.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepository;

    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart findById(String cartId) throws Exception {
        Optional<Cart> cart = cartRepository.findById(cartId);
        return cart.orElseThrow(()-> new Exception("todo"));
    }

    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    public void delete(String cartId) {
        cartRepository.deleteById(cartId);
    }

    public Cart update(Cart cart) {
        return cartRepository.save(cart);
    }
}
