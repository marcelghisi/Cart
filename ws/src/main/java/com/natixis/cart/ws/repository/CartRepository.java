package com.natixis.cart.ws.repository;

import com.natixis.cart.ws.domain.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart,String> {

}
