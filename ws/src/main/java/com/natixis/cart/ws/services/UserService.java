package com.natixis.cart.ws.services;

import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(String userId) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        return user.orElseThrow(()-> new Exception("todo"));
    }

    public Optional<List<User>> findByEmail(String email) throws Exception {
        List<User> users = userRepository.findByEmail(email);
        return CollectionUtils.isEmpty(users) ? Optional.empty() : Optional.ofNullable(users);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(String userId) {
        userRepository.deleteById(userId);
    }

    public User update(User user) {
        return userRepository.save(user);
    }
}
