package com.application.crud.controllers;

import com.application.crud.dao.UserDaoHibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDaoHibernate userDaoHibernate;

    public UsersController(UserDaoHibernate userDaoHibernate) {
        this.userDaoHibernate = userDaoHibernate;
    }

    @GetMapping()
    public String index(Model model) {
        // получение пользователей из DAO и передача на отображение
        model.addAttribute("users", userDaoHibernate.getAllUsers());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        // Получение одного человека по id из DAO и передача его на отображение
        model.addAttribute("user", userDaoHibernate.getUserById(id));
        return "users/show";
    }

}
