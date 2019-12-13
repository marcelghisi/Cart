package com.natixis.cart.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.cart.ws.domain.*;
import com.natixis.cart.ws.exception.RuleException;
import com.natixis.cart.ws.rules.CartRules;
import com.natixis.cart.ws.services.PurchaseCartHistoryService;
import com.natixis.cart.ws.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
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
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersCartControllerTest {

    @Autowired
    MockMvc mockMvc;

   @MockBean
   UserService userService;

   @MockBean
   PurchaseCartHistoryService purchaseCartHistoryService;

   @Mock
    CartRules cartRules;

   private static final String URL_API_USER_CART = "/api/users/123456/cart";
    private static final String URL_API_USER_CART_DELETE = "/api/users/123456/cart/111";
    private static final String URL_API_USER_CART_FINALIZE = "/api/users/123456/cart/checkout";
    private static final String URL_API_USER_CART_RESUME = "/api/users/123456/cart/resume";
    private static final String URL_API_USER_CART_HISTORY = "/api/users/123456/cart/history";
    private static final String USER_ID = "123456";

   @Before
   public void setup(){

   }

    @Test
    public void test_AddItem_ToCart_User() throws Exception {
        Product product = Product.builder().id("1112").name("TV").price(5.0).build();
        Item item = Item.builder().product(product).quantity(2).build();
        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        Cart cart = Cart.builder().items(itemList).totalValue(20.0).build();
        User user1 = User.builder().cart(cart).id(USER_ID).firstName("Marcel Jose").email("marcel.ghisi@gmail.com").build();
        Item item2 = Item.builder().product(product).quantity(3).build();
        Cart cart2 = Cart.builder().items(Arrays.asList(item2)).totalValue(20.0).build();
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart2).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user1);
        BDDMockito.given(this.userService.update(Mockito.any(User.class))).willReturn(user2);
        BDDMockito.doNothing().when(this.cartRules).validatePrice(Mockito.any(Item.class));
       mockMvc.perform(MockMvcRequestBuilders.post(URL_API_USER_CART)
                .content(createCartFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart.items[0].product.id").value(cart2.getItems().get(0).getProduct().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart.items[0].quantity").value(cart2.getItems().get(0).getQuantity()));
    }

    @Test
    public void test_AddItem_Menor_Zero_ToCart_User() throws Exception {
        Product product = Product.builder().id("111").name("TV").price(2.0).build();
        Item item = Item.builder().product(product).quantity(1).build();
        List<Item> lista = new ArrayList<>();
        lista.add(item);
        Cart cart = Cart.builder().totalValue(2.0).items(lista).build();
        User user  = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user);
        BDDMockito.given(this.userService.create(Mockito.any(User.class))).willReturn(user);
        BDDMockito.doThrow(new RuleException("fsd")).when(this.cartRules).validatePrice(Mockito.any(Item.class));
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
        Product product = Product.builder().id("111").name("TV").price(5.0).build();
        Item item = Item.builder().quantity(5).product(product).build();
        Cart cart = Cart.builder().items(Arrays.asList(item)).totalValue(5.0).build();

        Item itemUpdated = Item.builder().quantity(10).product(product).build();
        Cart cartUpdated = Cart.builder().items(Arrays.asList(itemUpdated)).totalValue(10.0).build();

        User user = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart).email("marcel.ghisi@gmail.com").build();
        User userUpdated = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cartUpdated).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user);
        BDDMockito.given(this.userService.update(Mockito.any(User.class))).willReturn(userUpdated);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_API_USER_CART)
                .content(createCartFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart.items[0].product.id").value(itemUpdated.getProduct().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart.items[0].quantity").value(itemUpdated.getQuantity()));
    }

    @Test
    public void test_ExcludeItem_ToCart_User() throws Exception {
        Product product = Product.builder().id("111").name("TV").price(5.0).build();
        Item item = Item.builder().quantity(1).product(product).build();
        List<Item> lista = new ArrayList<>(0);
        lista.add(item);
        Cart cart = Cart.builder().totalValue(5.0).items(lista).build();
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user2);
        mockMvc.perform(MockMvcRequestBuilders.delete(URL_API_USER_CART_DELETE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void test_Resume_UserCart_Ordered_History() throws Exception {
        Product product = Product.builder().id("111").name("TV").price(5.0).build();
        Item item = Item.builder().quantity(1).product(product).build();
        Cart cart = Cart.builder().totalValue(2.0).items(Arrays.asList(item)).build();
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart).email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user2);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER_CART_RESUME)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void test_ClosePurchase_Cleaning_UserCart_And_Saving_Cart_History() throws Exception {
        Product product = Product.builder().id("111").name("TV").price(5.0).build();
        Item item = Item.builder().quantity(1).product(product).build();
        Cart cart = Cart.builder().items(Arrays.asList(item)).totalValue(2.0).build();
        User user2 = User.builder().id(USER_ID).firstName("Marcel Jose").cart(cart).email("marcel.ghisi@gmail.com").build();
        PurchaseCartHistory purchase = PurchaseCartHistory.builder()
                .purchaseDate(new Date())
                .status("SUCCESS")
                .user(user2)
                .id("1111")
                .build();
        User userUpdated = User.builder().id(USER_ID).firstName("Marcel Jose").email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user2);
        BDDMockito.given(this.userService.update(Mockito.any())).willReturn(userUpdated);
        BDDMockito.given(this.purchaseCartHistoryService.create(Mockito.any())).willReturn(purchase);
        mockMvc.perform(MockMvcRequestBuilders.post(URL_API_USER_CART_FINALIZE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cart").doesNotExist())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    @Test
    public void test_List_All_User_List_Purchases_From_DataBase() throws Exception {
        User user1 = User.builder().id(USER_ID).firstName("Marcel").email("marcel.ghisi@gmail.com").build();
        PurchaseCartHistory purchaseCartHistory = PurchaseCartHistory.builder().user(user1).status("PENDING").purchaseDate(new Date()).id("111").build();
        BDDMockito.given(this.purchaseCartHistoryService.findByUserId(Mockito.anyString())).willReturn(Arrays.asList(purchaseCartHistory));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER_CART_HISTORY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(purchaseCartHistory.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].status").value("PENDING"));
    }

    @Test
    public void test_List_All_User_List_Empty_Purchases_From_DataBase() throws Exception {
        User user1 = User.builder().id(USER_ID).firstName("Marcel").email("marcel.ghisi@gmail.com").build();
        PurchaseCartHistory purchaseCartHistory = PurchaseCartHistory.builder().user(user1).status("PENDING").purchaseDate(new Date()).id("111").build();
        BDDMockito.given(this.purchaseCartHistoryService.findByUserId(Mockito.anyString())).willReturn(new ArrayList<>(0));

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER_CART_HISTORY)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("SUCCESS"));
    }

    private String createCartFromJson() throws JsonProcessingException {
        Product product = Product.builder().id("111").name("Sansung TV").price(2.0).build();
        Item item = Item.builder().quantity(1).product(product).build();
        Cart cart = Cart.builder().items(Arrays.asList(item)).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(item);
        return json;
    }

    private String createNegativeCartFromJson() throws JsonProcessingException {
        Product product = Product.builder().id("111").name("Sansung TV").price(-2.0).build();
        Item item = Item.builder().product(product).quantity(9).build();
        Cart cart = Cart.builder().totalValue(-2.0).items(Arrays.asList(item)).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(item);
        return json;
    }

}
