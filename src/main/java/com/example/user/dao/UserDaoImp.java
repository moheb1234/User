package com.example.user.dao;

import com.example.user.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDaoImp implements UserDao {

    private final UserRepository repository;

    public UserDaoImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }


    @Override
    public int delete(int id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
