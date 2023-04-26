package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.Service.UserDetailsServ;
import ru.kata.spring.boot_security.demo.security.UDimpl;

@Controller
public class StartPageController {
    @GetMapping()
    public String hello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UDimpl userDetails = (UDimpl) authentication.getPrincipal();
        System.out.println(userDetails.getUsername());

        return "hello";
    }
}
