package com.example.user.repository;


import com.example.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.firstname = :firstname , u.lastname = :lastname, u.email = :email where u.id = :id")
    void update(int id, String firstname, String lastname, String email);
}
