package com.example.user;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/load/{firstname}")
    public User user(@PathVariable String firstname) {
        return repository.user(firstname);
    }

    @GetMapping("/like/{pattern}")
    public List<User> userLike(@PathVariable String pattern) {
        return repository.all(pattern);
    }
}
