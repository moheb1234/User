package com.example.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;


public interface UserRepository extends JpaRepository<User, String> {

    User findByEmail(String email);
}
