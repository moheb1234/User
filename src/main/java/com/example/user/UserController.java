package com.example.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/load")
    public List<User> users() {
        return repository.findAll();
    }

    @PostMapping("/save")
    User save(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String email) {
        User user = new User(firstname, lastname, email);
        return repository.save(user);
    }
}
