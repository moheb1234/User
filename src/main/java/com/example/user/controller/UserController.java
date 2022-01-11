package com.example.user.controller;

import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.user.http_request_resource.UserUri.*;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService model) {
        this.service = model;
    }

    @GetMapping(find_all_user)
    public List<User> users() {
        return service.findAll();
    }

    @PostMapping(create_user)
    User save(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email) {
        try {
            return service.save(id, firstname, lastname, email);
        } catch (EmailException | DuplicateValueException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping(find_user_by_id)
    User getOneUser(@PathVariable() int id) {
        try {
            return service.findById(id);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping(update_user)
    User updateUSer(@PathVariable int id, @RequestBody User newUser) {
        try {
            return service.update(id, newUser);
        } catch (UserNotFoundException | EmailException | DuplicateValueException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(find_user_by_email)
    User findByEmail(@PathVariable String email) {
        try {
            return service.findByEmail(email);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping(delete_user)
    int deleteUser(@PathVariable int id) {
        try {
            return service.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
