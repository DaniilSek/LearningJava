package com.application.crud.controllers;

import com.application.crud.model.User;
import com.application.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/auth/registration")
    public String registration(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/auth/registration")
    public String registerNewUser(User user) {
        user.addRole("ROLE_USER");
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/admin/users_page")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllusers());
        return "users_page";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUserById(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/users/admin/users_page";
    }

    @GetMapping("/admin/add_user")
    public String showAddUserForm(@ModelAttribute("user") User user) {
        //model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/admin/addNew")
    public String createUser(User user) {
        userService.save(user);
        return "redirect:/users/admin/users_page";
    }

    @GetMapping("/admin/edit_user/{id}")
    public String showEditUserForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "edit_user";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUserById(@PathVariable("id") long id, User user) {
        user.setId(id);
        userService.save(user);
        return "redirect:/users/admin/users_page";
    }

}
