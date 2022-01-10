package com.example.user.Test;


import com.example.user.controller.UserController;
import com.example.user.model.User;
import com.example.user.service.UserService;
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
import java.util.Objects;

import static com.example.user.tools.JsonTools.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService model;

    @Test
    void save() throws Exception {
        User user = new User(1, "moheb", "moallem", "@gmail6");
        when(model.save(1, user.getFirstname(), user.getLastname(), user.getEmail())).thenReturn(user);
        MockHttpServletRequestBuilder request = post("/users").param("id", 1 + "").param("firstname", user.getFirstname()).param("lastname", user.getLastname()).param("email", user.getEmail());
        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        User resultUser = jsonToObject(json, User.class);
        assert Objects.requireNonNull(resultUser).equals(user);
    }

    @Test
    void users() throws Exception {
        User user = new User(1, "1", "1", "@1");
        User user2 = new User(2, "2", "2", "@2");
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);
        when(model.findAll()).thenReturn(users);
        MvcResult response = mockMvc.perform(get("/users")).andExpect(status().isOk()).andReturn();
        String json = response.getResponse().getContentAsString();
        User[] userArray = jsonToArray(json, User[].class);
        assert users.get(0).equals(Objects.requireNonNull(userArray)[0]);
    }

    @Test
    void getOneUser() throws Exception {
        User user = new User(1, "1", "1", "1");
        when(model.findById(user.getId())).thenReturn(user);
        MvcResult result = mockMvc.perform(get("/users/1")).andExpect(status().isOk()).andReturn();
        String body = result.getResponse().getContentAsString();
        User resultUser = jsonToObject(body, User.class);
        assert user.equals(resultUser);
    }

    @Test
    void updateUSer() throws Exception {
        User newUser = new User(1, "2", "2", "@10");
        when(model.update(newUser.getId(), newUser)).thenReturn(newUser);
        String json = toJson(newUser);
        MvcResult response = mockMvc.perform(put("/users/1").content(Objects.requireNonNull(json)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        User resultUser = jsonToObject(response.getResponse().getContentAsString(), User.class);
        assert Objects.equals(resultUser, newUser);
    }

    @Test
    void findByEmail() throws Exception {
        User user = new User(1, "1", "2", "@3");
        when(model.findByEmail(user.getEmail())).thenReturn(user);
        MvcResult result = mockMvc.perform(get("/users/find-by-email/@3")).andExpect(status().isOk()).andReturn();
        String json = result.getResponse().getContentAsString();
        User foundedUser = jsonToObject(json, User.class);
        assert Objects.requireNonNull(foundedUser).toString().equals(user.toString());
    }

    @Test
    void deleteUser() throws Exception {
        User user = new User(1, "3", "4", "alimoheb@gmail.com");
        when(model.delete(user.getId())).thenReturn(1);
        MvcResult response = mockMvc.perform(delete("/users/1")).andExpect(status().isOk()).andReturn();
        String body = response.getResponse().getContentAsString();
        assert body.equals("1");
    }
}