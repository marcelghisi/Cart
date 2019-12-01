package com.natixis.cart.ws;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    MockMvc mockMvc;

   @MockBean
   UserService userService;

   private static final String URL_API_USER = "/api/users";
    private static final String USER_ID = "123123123";

   @Before
   public void setup(){

   }

    @Test
    public void test_Update_User() throws Exception {
        User user = User.builder().id(USER_ID).firstName("Marcel Jose").email("marcel.ghisi@gmail.com").build();
        BDDMockito.given(this.userService.update(Mockito.any(User.class))).willReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put(URL_API_USER)
                .content(createUserFromJson())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }
   @Test
    public void test_Insert_New_User_ToDataBase() throws Exception {
       User user = User.builder().id(USER_ID).firstName("Marcel").email("marcel.ghisi@gmail.com").build();
       BDDMockito.given(this.userService.create(Mockito.any(User.class))).willReturn(user);
       mockMvc.perform(MockMvcRequestBuilders.post(URL_API_USER)
               .content(createUserFromJson())
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print())
               .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
               .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
   }

    @Test
    public void test_List_One_New_User_From_DataBase() throws Exception {

        User user = User.builder().id(USER_ID).firstName("Marcel").email("marcel.ghisi@gmail.com").build();

        BDDMockito.given(this.userService.findById(Mockito.anyString())).willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER+"/"+USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(user.getEmail()));
    }

    @Test
    public void test_List_All_New_User_From_DataBase() throws Exception {
        User user1 = User.builder().id(USER_ID).firstName("Marcel").email("marcel.ghisi@gmail.com").build();
        User user2 = User.builder().id(USER_ID+"2").firstName("Cristian").email("cristian.ghisi@gmail.com").build();

        List<User> users = Arrays.asList(user1,user2);

        BDDMockito.given(this.userService.findAll()).willReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(user1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id").value(user2.getId()));
    }

    @Test
    public void test_Delete_User_From_DataBase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_API_USER+"/"+USER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    private String createUserFromJson() throws JsonProcessingException {
        User user = User.builder().firstName("Marcel").email("marcel.ghisi@gmail.com").build();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);
        return json;
   }

}
