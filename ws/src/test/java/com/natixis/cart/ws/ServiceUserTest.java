package com.natixis.cart.ws;

import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.repository.UserRepository;
import com.natixis.cart.ws.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ServiceUserTest {

    public static final String CAD_246_E_13_DE_11_EA_8_D_71_362_B_9_E_155667 = "3cad246e-13de-11ea-8d71-362b9e155667";

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void test_UserCreation_save_In_Mongo(){
        User user = User.builder().firstName("Marcel Ghisi").email("marcel.ghisi").id(CAD_246_E_13_DE_11_EA_8_D_71_362_B_9_E_155667).build();

        when(userRepository.save(user)).thenReturn(user);

        User newOne = userService.create(user);

        Assert.assertEquals(user.getId(),newOne.getId());

    }

}
