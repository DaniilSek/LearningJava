package com.application.crud.controllers;

import com.application.crud.model.User;
import com.application.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("user") User user) {
        return "login";
    }

    @GetMapping("/register")
    public String registration(@ModelAttribute("user") User user) {
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(User user) {
        userService.save(user);
        return "redirect:/auth/login";
    }
}
