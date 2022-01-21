package com.example.user.dao;

import com.example.user.model.User;

import java.util.List;


public interface UserDao {

    User save(User user);

    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    int delete(int id);

}
