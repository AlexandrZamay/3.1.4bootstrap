package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private RoleService roleService;
    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public AdminController(RoleService roleService, UserService userService, UserValidator userValidator) {
        this.roleService = roleService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin?error=name";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping()
    public String showAdminPage(@RequestParam(required = false) String error, Model usersModel, Model adminModel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User admin = (User) authentication.getPrincipal();
        usersModel.addAttribute("users", userService.getAllUsers());
        adminModel.addAttribute("admin", admin);
        adminModel.addAttribute("newUser", new User());
        adminModel.addAttribute("roles",roleService.findAll() );
        if (error != null)
            adminModel.addAttribute("error", error);
        return "admin/pageForAdmin";
    }


    @PatchMapping("/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/admin?error=name";
        }
        userService.update(user.getId(), user);
        return "redirect:/admin";
    }

    @DeleteMapping()
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }


}



