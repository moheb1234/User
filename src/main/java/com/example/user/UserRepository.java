package com.example.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.firstname  = ?1")
      User user(String firstname);

    @Query(value = "select u from User u where u.firstname like %?1%")
    List<User> all(String pattern);
}
