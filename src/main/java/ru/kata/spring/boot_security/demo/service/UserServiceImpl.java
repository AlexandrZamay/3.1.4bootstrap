package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl  implements  UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public boolean saveUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }


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
//        User user = new User();
//        user.setId(id);
//        user.setRoles(editedUser.getRoles());
//        user.setName(editedUser.getName());
//        user.setEmail(editedUser.getEmail());
//        user.setUsername(editedUser.getUsername());
//        user.setYearOfBirth(editedUser.getYearOfBirth());
//        user.setPassword(passwordEncoder.encode(editedUser.getPassword()));
//        user.setRoles(editedUser.getRoles());
//        userRepository.save(user);
//    }
//}

        editedUser.setId(id);
        editedUser.setPassword(userRepository.getById(id).getPassword());
        //TODO вынести encoder в отдельный метод
        User user = getUser(id);
        Set<Role> existRole = user.getRoles();
        Set<Role> newRole = editedUser.getRoles();
        if (!existRole.containsAll(newRole)) {
            existRole.addAll(newRole);
        }
        editedUser.setRoles(existRole);
        userRepository.save(editedUser);
    }
}


