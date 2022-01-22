package com.example.user.controller;

import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
import com.example.user.exception.InputException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.User;
import com.example.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.user.http_request_resource.UserUri.*;

@RestController
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping(find_all_user)
    public ResponseEntity<List<User>> users() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @PostMapping(create_user)
    public ResponseEntity<User> save(@RequestParam int id, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email)
            throws DuplicateValueException, EmailException, InputException {
        return new ResponseEntity<>(service.save(id, firstname, lastname, email), HttpStatus.CREATED);
    }

    @GetMapping(find_user_by_id)
    public ResponseEntity<User> getOneUser(@PathVariable() int id) throws UserNotFoundException {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping(update_user)
    public ResponseEntity<User> updateUSer(@PathVariable int id, @RequestBody User newUser)
            throws UserNotFoundException, DuplicateValueException, InputException, EmailException {
        return new ResponseEntity<>(service.update(id, newUser), HttpStatus.OK);
    }

    @GetMapping(find_user_by_email)
    public ResponseEntity<User> findByEmail(@PathVariable String email) throws UserNotFoundException {
        return new ResponseEntity<>(service.findByEmail(email), HttpStatus.OK);
    }

    @DeleteMapping(delete_user)
    public ResponseEntity<Integer> deleteUser(@PathVariable int id) throws UserNotFoundException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }
}
