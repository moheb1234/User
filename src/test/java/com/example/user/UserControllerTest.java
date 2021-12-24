package com.example.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.user.JsonTools.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRepository repository;


    @Test
    void save() throws Exception {
        User user = new User("moheb", "moallem", "@gmail6");
        when(repository.save(user)).thenReturn(user);
        MockHttpServletRequestBuilder request = post("/save").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("firstname", user.getFirstname())
                .param("lastname", user.getLastname())
                .param("email", user.getEmail());
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void users() throws Exception {
        User user = new User("1", "1", "@1");
        User user2 = new User("2", "2", "@2");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        when(repository.findAll()).thenReturn(users);
        MvcResult response = mockMvc.perform(get("/load"))
                .andExpect(status().isOk()).andReturn();
        String json = response.getResponse().getContentAsString();
        User[] userArray = jsonToArray(json, User[].class);
        assert userArray != null;
        for (int i = 0; i < users.size(); i++) {
            assert users.get(i).toString().equals(userArray[i].toString());
        }
    }

    @Test
    void getOneUser() throws Exception {
        User user = new User("1", "1", "1");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        MvcResult result = mockMvc.perform(get("/load/byId/0"))
                .andExpect(status().isOk()).andReturn();
        String body = result.getResponse().getContentAsString();
        User resultUser = jsonToObject(body, User.class);
        assert resultUser != null;
        assert resultUser.toString().equals(user.toString());
    }

    @Test
    void updateUSer() throws Exception {
        User newUser = new User("2", "2", "@10");
        when(repository.updateUser(newUser.getFirstname(), newUser.getFirstname(), newUser.getEmail(), 0))
                .thenReturn(1);
        String json = toJson(newUser);
        assert json != null;
        mockMvc.perform(put("/update/0").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findByEmail() throws Exception {
        User user = new User("1", "2", "@3");
        when(repository.findByEmail(user.getEmail())).thenReturn(user);
        MvcResult result = mockMvc.perform(get("/load/byEmail/@3"))
                .andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        User foundedUser = jsonToObject(json,User.class);
        assert foundedUser!=null;
        assert foundedUser.toString().equals(user.toString());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/delete/1")).andExpect(status().isOk());
    }

    @Test
    void saveV2() throws Exception {
        User user = new User("1,", "2", "@3");
        String json = toJson(user);
        when(repository.save(user)).thenReturn(user);
        assert json != null;
        MockHttpServletRequestBuilder request = post("/saveV2").content(json).contentType(MediaType.APPLICATION_JSON);
         mockMvc.perform(request).andExpect(status().isOk());
    }
}