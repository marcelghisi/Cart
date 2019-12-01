package com.natixis.cart.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.natixis.cart.ws.domain.Item;
import com.natixis.cart.ws.services.ItemService;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {

    public static final String ITEM_ID = "123456";
    @Autowired
    MockMvc mockMvc;

   @MockBean
   ItemService itemService;

   private static final String URL_API_ITEM = "/api/items";

   @Before
   public void setup(){

   }

    @Test
    public void test_Update_User() throws Exception {
        Item item = Item.builder().id("1").name("TV").price(300.00).build();
        BDDMockito.given(this.itemService.update(Mockito.any(Item.class))).willReturn(item);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_API_ITEM)
                .content(createUserFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));
    }

   @Test
    public void test_Insert_New_User_ToDataBase() throws Exception {
       Item item = Item.builder().id("1").name("TV").price(300.00).build();
       BDDMockito.given(this.itemService.create(Mockito.any(Item.class))).willReturn(item);
       mockMvc.perform(MockMvcRequestBuilders.post(URL_API_ITEM)
               .content(createUserFromJson())
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));
   }

    @Test
    public void test_List_One_New_User_From_DataBase() throws Exception {
        Item item = Item.builder().id("1").name("TV").price(300.00).build();
        BDDMockito.given(this.itemService.findById(Mockito.anyString())).willReturn(item);
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_ITEM +"/"+ ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(item.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(item.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(item.getPrice()));
    }

    @Test
    public void test_List_All_New_User_From_DataBase() throws Exception {
        Item item1 = Item.builder().id("1").name("TV").price(300.00).build();
        Item item2 = Item.builder().id("1").name("TV").price(300.00).build();

        List<Item> itens = Arrays.asList(item1,item2);

        BDDMockito.given(this.itemService.findAll()).willReturn(itens);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_ITEM)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(item1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(item2.getId()));
    }

    @Test
    public void test_Delete_User_From_DataBase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_ITEM +"/"+ITEM_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    private String createUserFromJson() throws JsonProcessingException {
        Item item = Item.builder().id("1").name("TV").price(300.00).build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(item);
        return json;
   }

}
