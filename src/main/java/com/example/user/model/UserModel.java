package com.example.user.model;

import com.example.user.entities.User;
import com.example.user.exception.EmailException;
import com.example.user.exception.EmailIsExistsException;
import com.example.user.exception.UserNotFoundException;
import com.example.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.user.exception.EmailException.emailIsValid;

@Service
public class UserModel {

    UserRepository repository;

    public UserModel(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User save(String firstname, String lastname, String email) throws EmailException, EmailIsExistsException {
        if (!emailIsValid(email))
            throw new EmailException("email is not valid");
        if (repository.findByEmail(email) != null)
            throw new EmailIsExistsException("email is already exist");
        return repository.save(new User(firstname, lastname, email));
    }

    public User findById(String id) throws UserNotFoundException {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException("user not exists");
        return user.get();
    }

    public User update(String id, User newUser) throws UserNotFoundException, EmailException, EmailIsExistsException {
        User user = findById(id);
        user.setAll(newUser.getFirstname(), newUser.getLastname(), newUser.getEmail());
        if (!emailIsValid(newUser.getEmail()))
            throw new EmailException("email is not valid");
        if (findByEmail(newUser.getEmail()) == null || newUser.getEmail().equals(user.getEmail()))
            return repository.save(user);
        throw new EmailIsExistsException("new email is already exist");
    }

    public User findByEmail(String email) throws UserNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException("user not exist");
        return user;
    }

    public int delete(String id) throws UserNotFoundException {
        if (findById(id) != null) {
            repository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
