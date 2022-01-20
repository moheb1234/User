package com.example.user.dao;

import com.example.user.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserDao {

    User save(User user);

    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    void update(int id, User newUser);

    int delete(int id);

}
