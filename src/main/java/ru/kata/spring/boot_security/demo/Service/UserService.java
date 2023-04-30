package ru.kata.spring.boot_security.demo.Service;


import ru.kata.spring.boot_security.demo.Model.User;

import java.util.List;



public interface UserService {
    boolean saveUser(User user);

    User getUser(long id);

    List<User> getAllUsers();

    void delete(long id);

    void update(long id, User editedUser);
}
