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
    Optional<User> getOneUser(@PathVariable int id) {
        return repository.findById(id);
    }

    @PutMapping("/save/{id}")
    void updateUSer(@PathVariable int id, @RequestBody User newUser) {
        if (repository.findByEmail(newUser.getEmail())==null){
        repository.updateUser(newUser.getFirstname(), newUser.getLastname(), newUser.getEmail(), id);
        }
    }

    @GetMapping("/load/byEmail/{email}")
    User findByEmail(@PathVariable String email){
        return repository.findByEmail(email);
    }

    @DeleteMapping("/delete/{id}")
    void deleteUser(@PathVariable int id){
        repository.deleteById(id);
    }
}
