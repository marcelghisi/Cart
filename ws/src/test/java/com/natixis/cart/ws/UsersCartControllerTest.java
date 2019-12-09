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
    private static final String URL_API_USER_CART_RESUME = "/api/users/123456/cart/resume";
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
    public void test_AddItem_Menor_Zero_ToCart_User() throws Exception {
        Item item = Item.builder().id("111").name("TV").price(2.0).build();
        Cart cart = Cart.builder().quantidade(1).item(item).build();
        User user  = User.builder().id(USER_ID).firstName("Marcel Jose").cart(Arrays.asList(cart)).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user);
        BDDMockito.given(this.userService.create(Mockito.any(User.class))).willReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_API_USER_CART)
                .content(createNegativeCartFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0]").value("Value needs to be positive"));
    }

    @Test
    public void test_UpdateItem_ToCart_User() throws Exception {
        Item item = Item.builder().id("111").name("TV").price(5.0).build();
        Cart cart = Cart.builder().quantidade(1).item(item).build();
        Cart cartUpdated = Cart.builder().quantidade(9).item(item).build();
        User user = User.builder().id(USER_ID).firstName("Marcel Jose").cart(Arrays.asList(cart)).email("marcel.ghisi@gmail.com").build();
        User userUpdated = User.builder().id(USER_ID).firstName("Marcel Jose").cart(Arrays.asList(cartUpdated)).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user);
        BDDMockito.given(this.userService.update(Mockito.any(User.class))).willReturn(userUpdated);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_API_USER_CART)
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

    @Test
    public void test_Resume_UserCart_Ordered_History() throws Exception {
        Item item = Item.builder().id("111").name("TV").price(5.0).build();
        Cart cart = Cart.builder().quantidade(1).item(item).build();
        List<Cart> cartList = new ArrayList<>(0);
        cartList.add(cart);
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cartList).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user2);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER_CART_RESUME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void test_ClosePurchase_Cleaning_UserCart_And_Saving_Cart_History() throws Exception {
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
        Item item = Item.builder().id("111").name("Sansung TV").price(2.0).build();
        Cart cart = Cart.builder().item(item).quantidade(9).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cart);
        return json;
    }

    private String createNegativeCartFromJson() throws JsonProcessingException {
        Item item = Item.builder().id("111").name("Sansung TV").price(-2.0).build();
        Cart cart = Cart.builder().item(item).quantidade(9).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cart);
        return json;
    }

}
