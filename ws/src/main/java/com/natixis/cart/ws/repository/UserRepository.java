package com.natixis.cart.ws.repository;

import com.natixis.cart.ws.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findByEmail(String email);

}
