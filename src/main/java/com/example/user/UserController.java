package com.example.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/load/byId/{id}")
    Optional<User> getOneUser(@PathVariable String id) {
        return repository.findById(id);
    }

    @PutMapping("/update/{id}")
    User updateUSer(@PathVariable String id, @RequestBody User newUser) throws Exception {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("user not found");
        } else {
            user.get().update(newUser);
            return repository.save(user.get());
        }
    }

    @GetMapping("/load/byEmail/{email}")
    User findByEmail(@PathVariable String email) {
        return repository.findByEmail(email);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable String id) {
        repository.deleteById(id);
    }

    @PostMapping("/saveV2")
    User saveV2(@RequestBody User user) {
        return repository.save(user);
    }
}
