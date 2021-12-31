package com.example.user.model;

import com.example.user.entities.User;
import com.example.user.exception.DuplicateValueException;
import com.example.user.exception.EmailException;
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

    public User save(int id, String firstname, String lastname, String email) throws EmailException, DuplicateValueException {
        if (repository.findById(id).isPresent())
            throw new DuplicateValueException(id);
        if (!emailIsValid(email))
            throw new EmailException(email);
        if (repository.findByEmail(email) != null)
            throw new DuplicateValueException(email);
        return repository.save(new User(id, firstname, lastname, email));
    }

    public User findById(int id) throws UserNotFoundException {
        Optional<User> user = repository.findById(id);
        if (user.isEmpty())
            throw new UserNotFoundException(id);
        return user.get();
    }

    public User update(int id, User newUser) throws UserNotFoundException, EmailException, DuplicateValueException {
        User user = findById(id);
        user.setAll(newUser.getFirstname(), newUser.getLastname(), newUser.getEmail());
        if (!emailIsValid(newUser.getEmail()))
            throw new EmailException(newUser.getEmail());
        if (findByEmail(newUser.getEmail()) == null || newUser.getEmail().equals(user.getEmail()))
            return repository.save(user);
        throw new DuplicateValueException(newUser.getEmail());
    }

    public User findByEmail(String email) throws UserNotFoundException {
        User user = repository.findByEmail(email);
        if (user == null)
            throw new UserNotFoundException(email);
        return user;
    }

    public int delete(int id) throws UserNotFoundException {
        if (findById(id) != null) {
            repository.deleteById(id);
            return 1;
        }
        return 0;
    }
}
