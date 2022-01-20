package com.example.user.service;

import com.example.user.dao.UserDao;
import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.user.exception.EmailException.emailIsValid;

@Service
public class UserService {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User save(int id, String firstname, String lastname, String email) throws EmailException, DuplicateValueException {
        if (userDao.findById(id) != null) {
            throw new DuplicateValueException(id);
        }
        if (!emailIsValid(email)) {
            throw new EmailException(email);
        }
        if (userDao.findByEmail(email) != null) {
            throw new DuplicateValueException(email);
        }
        return userDao.save(new User(id, firstname, lastname, email));
    }

    public User findById(int id) throws UserNotFoundException {
        User user = userDao.findById(id);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user;
    }

    public void update(int id, User newUser)  {
         userDao.update(id, newUser);
    }

    public User findByEmail(String email) throws UserNotFoundException {
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    public int delete(int id) throws UserNotFoundException {
        int result = userDao.delete(id);
        if (result == 0) {
            throw new UserNotFoundException(id);
        }
        return result;
    }
}
