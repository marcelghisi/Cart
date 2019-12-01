package com.natixis.cart.ws.config;

import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Optional;

public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    UserRepository userRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        User user = User.builder().id("3cad246e-13de-11ea-8d71-362b9e155667").firstName("Admin").email("marcel.ghisi@gmail.com").build();
        Optional<User> userFound = userRepository.findById(user.getId());
        if (!userFound.isPresent()){
            userRepository.save(user);
        }
    }
}
