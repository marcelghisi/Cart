package com.natixis.cart.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.repository.ProductRepository;
import com.natixis.cart.ws.services.ProductService;
import org.junit.Assert;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    public static final String ITEM_ID = "123456";
    @Autowired
    MockMvc mockMvc;

   @MockBean
   ProductService productService;

   @Mock
    ProductRepository productRepository;

   private static final String URL_API_PRODUCTS = "/api/products";

   @Before
   public void setup(){

   }

    @Test
    public void test_Update_User() throws Exception {
        Product product = Product.builder().id("1").name("TV").price(300.00).build();
        BDDMockito.given(this.productService.update(Mockito.any(Product.class))).willReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_API_PRODUCTS)
                .content(createUserFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()));
    }

   @Test
    public void test_Insert_New_User_ToDataBase() throws Exception {
       Product product = Product.builder().id("1").name("TV").price(300.00).build();
       BDDMockito.given(this.productService.create(Mockito.any(Product.class))).willReturn(product);
       mockMvc.perform(MockMvcRequestBuilders.post(URL_API_PRODUCTS)
               .content(createUserFromJson())
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()));
   }

    @Test
    public void test_List_One_New_User_From_DataBase() throws Exception {
        Product product = Product.builder().id("1").name("TV").price(300.00).build();
        BDDMockito.given(this.productService.findById(Mockito.anyString())).willReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_PRODUCTS +"/"+ ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(product.getPrice()));
    }

    @Test
    public void test_findById() throws Exception {
       Product product = Product.builder().id("1111").name("Sansung").price(2.4).build();
       BDDMockito.given(this.productRepository.findById(Mockito.any())).willReturn(Optional.of(product));
       BDDMockito.given(this.productService.findById(Mockito.any())).willReturn(product);
       mockMvc.perform(MockMvcRequestBuilders.get(URL_API_PRODUCTS+"/"+ITEM_ID)
       .contentType(MediaType.APPLICATION_JSON)
       .accept(MediaType.APPLICATION_JSON))
       .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(product.getId()));
       Product foundProduct = this.productService.findById(product.getId());
       Assert.assertEquals(product.getId(),foundProduct.getId());
    }

    @Test
    public void test_List_All_New_User_From_DataBase() throws Exception {
        Product product1 = Product.builder().id("1").name("TV").price(300.00).build();
        Product product2 = Product.builder().id("1").name("TV").price(300.00).build();

        List<Product> itens = Arrays.asList(product1, product2);

        BDDMockito.given(this.productService.findAll()).willReturn(itens);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_PRODUCTS)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(product1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(product2.getId()));
    }

    @Test
    public void test_Delete_User_From_DataBase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_PRODUCTS +"/"+ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    private String createUserFromJson() throws JsonProcessingException {
        Product product = Product.builder().id("1").name("TV").price(300.00).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(product);
        return json;
   }

}
