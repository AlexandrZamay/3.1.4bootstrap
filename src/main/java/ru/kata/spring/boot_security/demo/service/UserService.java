package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;



public interface UserService {
    boolean saveUser(User user);

    User getUser(long id);

    List<User> getAllUsers();

    void delete(long id);

    void update(long id, User editedUser);
}
