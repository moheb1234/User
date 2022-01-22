package com.example.user.Test;


import com.example.user.controller.UserController;
import com.example.user.dao.UserDao;
import com.example.user.model.User;
import com.example.user.service.UserService;
import com.example.user.tools.JsonTools;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static com.example.user.http_request_resource.UserUri.create_user;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserService.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    private UserDao userDao;


    // save user
    @Test
    void saveWithEmptyInput() throws Exception {
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "")
                .param("lastname", "")
                .param("email", "1@gmail.com")).andExpect(status().isBadRequest());
    }

    @Test
    void SaveWithDuplicateId() throws Exception {
        when(userDao.findById(1)).thenReturn(new User());
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", "ali@gmail.com")).andExpect(status().isNotAcceptable());
    }

    @Test
    void SaveWithDuplicateEmail() throws Exception {
        String email = "ali@gmail.com";
        when(userDao.findByEmail(email)).thenReturn(new User());
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", email)).andExpect(status().isNotAcceptable());
    }

    @Test
    void SaveWithNotValidEmail() throws Exception {
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", "xxxxx")).andExpect(status().isUnauthorized());
    }

    @Test
    void SaveWithSuccess() throws Exception {
        String email = "ali@gmail.com";
        when(userDao.findByEmail(email)).thenReturn(null);
        when(userDao.findById(1)).thenReturn(null);
        mvc.perform(post(create_user)
                .param("id", 1 + "")
                .param("firstname", "ali")
                .param("lastname", "ali")
                .param("email", email)).andExpect(status().isCreated());
    }

    // find by id
    @Test
    void findUserByIdThatIsNotExist() throws Exception {
        when(userDao.findById(1)).thenReturn(null);
        mvc.perform(get(create_user + "/1")).andExpect(status().isNotFound());
    }

    @Test
    void findUserByIdThatIstExist() throws Exception {
        when(userDao.findById(1)).thenReturn(new User());
        mvc.perform(get(create_user + "/1")).andExpect(status().isOk());
    }

    // find by email
    @Test
    void findUserByEmailThatIsNotExist() throws Exception {
        when(userDao.findByEmail("ali@gmail.com")).thenReturn(null);
        mvc.perform(get(create_user + "find-by-email/ali@gmail.com")).andExpect(status().isNotFound());
    }

    @Test
    void findByEmailThatIstExist() throws Exception {
        when(userDao.findByEmail("ali@gmail.com")).thenReturn(new User());
        mvc.perform(get(create_user + "/find-by-email/ali@gmail.com")).andExpect(status().isOk());
    }
    //update user

    @Test
    void updateWhenUserNotExist() throws Exception {
        User user = new User(1, "1", "1", "ali@gmail.com");
        when(userDao.findById(1)).thenReturn(null);
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateWithDuplicateEmail() throws Exception {
        User user = new User(1, "1", "1", "ali@gmail.com");
        when(userDao.delete(1)).thenReturn(1);
        when(userDao.findByEmail("ali@gmail.com")).thenReturn(new User());
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    void updateWithInvalidInput() throws Exception {
        User user = new User(1, "", "", "ali@gmail.com");
        when(userDao.delete(1)).thenReturn(1);
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    void updateWithInvalidEmail() throws Exception {
        User user = new User(1, "1", "1", "xxxxxxxx");
        when(userDao.delete(1)).thenReturn(1);
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateWithSuccess() throws Exception {
        User user = new User(1, "1", "1", "ali@gmail.com");
        when(userDao.delete(1)).thenReturn(1);
        when(userDao.findByEmail("ali@gmail.com")).thenReturn(null);
        mvc.perform(put(create_user + "/1")
                        .content(Objects.requireNonNull(JsonTools.toJson(user)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // delete user
    @Test
    void deleteUserThatNotExist() throws Exception {
       when(userDao.delete(1)).thenReturn(0);
        mvc.perform(delete(create_user + "/1")).andExpect(status().isNotFound());
    }

    @Test
    void deleteExistUser() throws Exception {
        when(userDao.delete(1)).thenReturn(1);
        mvc.perform(delete(create_user + "/1")).andExpect(status().isOk());
    }
}