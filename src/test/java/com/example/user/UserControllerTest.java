package com.example.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserRepository repository;

    @Test
    void save() {
        UserController userController = new UserController(repository);
        User user = userController.save("moheb", "moallem", "@gmail");
        assert user != null : "user is null";
    }
}