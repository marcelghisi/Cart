package com.natixis.cart.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.cart.ws.domain.Cart;
import com.natixis.cart.ws.domain.Item;
import com.natixis.cart.ws.domain.User;
import com.natixis.cart.ws.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersCartControllerTest {

    @Autowired
    MockMvc mockMvc;

   @MockBean
   UserService userService;

   private static final String URL_API_USER_CART = "/api/users/123456/cart";
    private static final String URL_API_USER_CART_DELETE = "/api/users/123456/cart/111";
    private static final String USER_ID = "123456";

   @Before
   public void setup(){

   }

    @Test
    public void test_AddItem_ToCart_User() throws Exception {
        Item item = Item.builder().id("111").name("TV").price(5.0).build();
        User user1 = User.builder().id(USER_ID).firstName("Marcel Jose").email("marcel.ghisi@gmail.com").build();
        Cart cart = Cart.builder().quantidade(1).item(item).build();
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(Arrays.asList(cart)).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user1);
        BDDMockito.given(this.userService.update(Mockito.any(User.class))).willReturn(user2);
       mockMvc.perform(MockMvcRequestBuilders.post(URL_API_USER_CART)
                .content(createCartFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart[0].item.id").value(cart.getItem().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart[0].quantidade").value(cart.getQuantidade()));
    }

    @Test
    public void test_ExcludeItem_ToCart_User() throws Exception {
        Item item = Item.builder().id("111").name("TV").price(5.0).build();
        Cart cart = Cart.builder().quantidade(1).item(item).build();
        List<Cart> cartList = new ArrayList<>(0);
        cartList.add(cart);
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cartList).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user2);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_API_USER_CART_DELETE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }



    private String createCartFromJson() throws JsonProcessingException {
        Item item = Item.builder().id("123456").name("Sansung TV").price(2.0).build();
        Cart cart = Cart.builder().item(item).quantidade(1).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cart);
        return json;
   }

}
