package com.application.crud.controllers;

import com.application.crud.model.Role;
import com.application.crud.model.User;
import com.application.crud.repositories.RoleRepository;
import com.application.crud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/users_page")
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

    @GetMapping("/edit_user/{id}")
    public String showEditUserForm(@PathVariable("id") long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("decodedPassword", user.getPassword());
        List<Role> allRoles = roleRepository.findAll(); // Получаем все возможные роли
        model.addAttribute("allRoles", allRoles);
        return "edit_user";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUserById(@PathVariable("id") long id, User user, @RequestParam(required = false) List<Long> roleIds) {
        user.setId(id);
        userService.updateUserRoles(user, roleIds);
        //userService.save(user);
        return "redirect:/users/users_page";
    }
}
