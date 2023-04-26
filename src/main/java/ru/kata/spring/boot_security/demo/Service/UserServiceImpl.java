package ru.kata.spring.boot_security.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Model.Role;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl  implements  UserService {


    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl( UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
       // user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //TODO хэширование пароля
        userRepository.save(user);
        return true;
    }
    @Transactional
    @Override
    public User getUser(long id) {
       return userRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void delete(long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    @Override
    public void update(long id, User editedUser) {
        editedUser.setId(id);
        userRepository.save(editedUser);
    }
}
