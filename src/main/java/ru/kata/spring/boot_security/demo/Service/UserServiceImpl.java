package ru.kata.spring.boot_security.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.Model.Role;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Repositories.UserRepository;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl  implements  UserService {

    private final RoleServiceImpl roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleServiceImpl roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
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
        editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword())); //TODO вынести encoder в отдельный метод
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


