package com.example.user.controller;

import com.example.user.entities.User;
import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.UserModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserModel model;

    public UserController(UserModel model) {
        this.model = model;
    }

    @GetMapping("/users")
    public List<User> users() {
        return model.findAll();
    }

    @PostMapping("/users")
    User save(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email) {
        try {
            return model.save(id, firstname, lastname, email);
        } catch (EmailException | DuplicateValueException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/users/{id}")
    User getOneUser(@PathVariable() int id) {
        try {
            return model.findById(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/users/{id}")
    User updateUSer(@PathVariable int id, @RequestBody User newUser) {
        try {
            return model.update(id, newUser);
        } catch (UserNotFoundException | EmailException | DuplicateValueException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/users/find-by-email/{email}")
    User findByEmail(@PathVariable String email) {
        try {
            return model.findByEmail(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/users/{id}")
    int deleteUser(@PathVariable int id) {
        try {
            return model.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
