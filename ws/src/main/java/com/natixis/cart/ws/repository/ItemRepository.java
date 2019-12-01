package com.natixis.cart.ws.repository;

import com.natixis.cart.ws.domain.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item,String> {

}
