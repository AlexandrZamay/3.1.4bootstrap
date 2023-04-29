package ru.kata.spring.boot_security.demo.Service;


import ru.kata.spring.boot_security.demo.Model.User;

import java.util.List;



public interface UserService {
    public boolean saveUser(User user);

    public User getUser(long id);

    public List<User> getAllUsers();

    public void delete(long id);

    public void update(long id, User editedUser);
}
