package com.example.user.Test;


import com.example.user.controller.UserController;
import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
import com.example.user.exception.InputException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.example.user.tools.JsonTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static com.example.user.http_request_resource.UserUri.create_user;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService service;


    // save user
    @Test
    void saveWithEmptyInput() throws Exception {
        when(service.save(1, "", "", "@gmail.com")).thenThrow(new InputException());
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "")
                .param("lastname", "")
                .param("email", "@gmail.com")).andExpect(status().isBadRequest());
    }

    @Test
    void SaveWithDuplicateId() throws Exception {
        when(service.save(1, "ali", "ali", "@gmail.com")).thenThrow(new DuplicateValueException(1));
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", "@gmail.com")).andExpect(status().isNotAcceptable());
    }

    @Test
    void SaveWithDuplicateEmail() throws Exception {
        String email = "@gmail.com";
        when(service.save(1, "ali", "ali", email)).thenThrow(new DuplicateValueException(email));
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", email)).andExpect(status().isNotAcceptable());
    }

    @Test
    void SaveWithNotValidEmail() throws Exception {
        String email = "xxxxxx";
        when(service.save(1, "ali", "ali", email)).thenThrow(new EmailException(email));
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", email)).andExpect(status().isUnauthorized());
    }

    @Test
    void SaveWithSuccess() throws Exception {
        User user = new User(1, "ali", "ali", "ali@gmail.com");
        when(service.save(1, "ali", "ali", "ali@gmail.com")).thenReturn(user);
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", "ali@gmail.com")).andExpect(status().isCreated());
    }

    // find by id
    @Test
    void findByIdThatIsNotExist() throws Exception {
        when(service.findById(1)).thenThrow(new UserNotFoundException(1));
        mvc.perform(get(create_user + "/1")).andExpect(status().isNotFound());
    }

    @Test
    void findByIdThatIstExist() throws Exception {
        User user = new User(1, "1", "1", "@gmail.com");
        when(service.findById(1)).thenReturn(user);
        mvc.perform(get(create_user + "/1")).andExpect(status().isOk());
    }

    // find by email
    @Test
    void findByEmailThatIsNotExist() throws Exception {
        when(service.findByEmail("@gmail.com")).thenThrow(new UserNotFoundException("@gmail.com"));
        mvc.perform(get(create_user + "find-by-email/@gmail.com")).andExpect(status().isNotFound());
    }

    @Test
    void findByEmailThatIstExist() throws Exception {
        User user = new User(1, "1", "1", "@gmail.com");
        when(service.findByEmail("@gmail.com")).thenReturn(user);
        mvc.perform(get(create_user + "/find-by-email/@gmail.com")).andExpect(status().isOk());
    }
    //update user

    @Test
    void updateWhenUserNotExist() throws Exception {
        User user = new User(1, "1", "1", "@gmail.com");
        when(service.update(1, user)).thenThrow(new UserNotFoundException(1));
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithDuplicateEmail() throws Exception {
        User user = new User(1, "1", "1", "@gmail.com");
        when(service.update(1, user)).thenThrow(new DuplicateValueException("@gmail.com"));
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void updateWithInvalidInput() throws Exception {
        User user = new User(1, "", "", "@gmail.com");
        when(service.update(1, user)).thenThrow(new InputException());
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateWithInvalidEmail() throws Exception {
        User user = new User(1, "", "", "@gmail.com");
        when(service.update(1, user)).thenThrow(new EmailException("@gmail.com"));
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateWithSuccess() throws Exception {
        User user = new User(1, "", "", "@gmail.com");
        when(service.update(1, user)).thenReturn(user);
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // delete user
    @Test
    void deleteUserThatNotExist() throws Exception {
        when(service.delete(1)).thenThrow(new UserNotFoundException(1));
        mvc.perform(delete(create_user + "/1")).andExpect(status().isNotFound());
    }

    @Test
    void deleteExistUser() throws Exception {
        when(service.delete(1)).thenReturn(1);
        mvc.perform(delete(create_user + "/1")).andExpect(status().isOk());
    }
}