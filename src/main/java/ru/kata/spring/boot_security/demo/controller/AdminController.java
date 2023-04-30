package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.Model.User;
import ru.kata.spring.boot_security.demo.Service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserValidator userValidator;
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "usersList";
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id")int id, Model model) {

        model.addAttribute("user", userService.getUser(id));
        return "pageForAdmin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.getUser(id));
        return "edit";

    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user")User user, @PathVariable("id") int id) {
        userService.update(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id")Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    private String createUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute ("user")@Valid User user, BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

}

//    @GetMapping()
//    public String hello(Model model) {
//        model.addAttribute("users", userService.getAllUsers());
//
//
//        return "usersList";
//    }
//    @GetMapping("/{id}")
//    public String show(@PathVariable("id")int id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "userPage";
//    }
//
//    @GetMapping("/new")
//    private String createUser(Model model) {
//        model.addAttribute("user", new User());
//
//        return "new";
//    }
//
//    @PostMapping()
//    public String create(@ModelAttribute("user")User user) {
//        userService.saveUser(user); //добавить метод
//        return "redirect:/list";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") int id) {
//        model.addAttribute("user", userService.getUser(id));
//        return "edit";
//
//    }
//
//    @PatchMapping("/{id}")
//    public String update(@ModelAttribute("user")User user, @PathVariable("id") int id) {
//        userService.update(id, user);
//        return "redirect:/list";
//    }
//    @Role(2)
//    @DeleteMapping("/{id}")
//    public String delete(@PathVariable("id")int id) {
//        userService.delete(id);
//        return "redirect:/list";
//    }
//
//}

