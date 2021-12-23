package com.example.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public interface UserRepository extends JpaRepository<User, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update User u set u.firstname = :firstname , u.lastname = :lastname , u.email = :email where u.id = :id ")
    int updateUser(String firstname, String lastname, String email, int id);

    User findByEmail(String email);

    @Override
    void deleteById(Integer integer);
}
