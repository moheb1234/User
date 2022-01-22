package com.example.user.dao;

import com.example.user.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDao {

    User save(User user);

    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

    int delete(int id);

}
