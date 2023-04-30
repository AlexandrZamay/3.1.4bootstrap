package ru.kata.spring.boot_security.demo.Service;

import ru.kata.spring.boot_security.demo.Model.Role;

public interface RoleService {
    Role findById(Long id);
}
