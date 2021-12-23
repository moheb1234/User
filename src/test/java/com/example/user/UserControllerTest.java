package com.example.user;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
        mockMvc.perform(post("/save")
                        .param("firstname", user.getFirstname())
                        .param("lastname", user.getLastname())
                        .param("email", user.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    void users() throws Exception {
        User user = new User("1", "1", "@1");
        List<User> users = new ArrayList<>();
        users.add(user);
        when(repository.findAll()).thenReturn(users);
        mockMvc.perform(get("/load"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email", is(user.getEmail())))
                .andExpect(jsonPath("$[0].lastname", is(user.getLastname())))
                .andExpect(jsonPath("$[0].firstname", is(user.getFirstname())));
    }

    @Test
    void getOneUser() throws Exception {
        User user = new User("1", "1", "1");
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        mockMvc.perform(get("/load/byId/0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.lastname", is(user.getLastname())))
                .andExpect(jsonPath("$.firstname", is(user.getFirstname())));
    }

//    @Test
//    void updateUSer() throws Exception {
//        User newUser = new User("2", "2", "@10");
//        when(repository.updateUser(newUser.getFirstname(),newUser.getFirstname(),newUser.getEmail(),0))
//                .thenReturn(1);
//        String json = JsonTools.toJson(newUser);
//        assert json != null;
//        mockMvc.perform(put("/update/0").content(json))
//                .andExpect(status().isOk());
//    }

    @Test
    void findByEmail() throws Exception {
        User user = new User("1","2","@3");
        when(repository.findByEmail(user.getEmail())).thenReturn(user);
        mockMvc.perform(get("/load/byEmail/@3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.lastname", is(user.getLastname())))
                .andExpect(jsonPath("$.firstname", is(user.getFirstname())));
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/delete/1")).andExpect(status().isOk());
    }
}