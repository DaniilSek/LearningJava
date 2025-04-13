package com.application.crud.services;

import com.application.crud.model.User;
import com.application.crud.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
//        if (userRepository.findByRolesContaining("ADMIN").isEmpty()) {
//            // Создаем нового пользователя с ролью ADMIN
//            User admin = new User();
//            admin.setName("admin");
//            //admin.setPassword(passwordEncoder.encode("admin"));
//            admin.setPassword("admin");
//            admin.setRoles(Collections.singleton("ADMIN"));
//
//            // Сохраняем пользователя admin в базу данных
//            userRepository.save(admin);
//        }

    }

    @Transactional
    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

//    public void updateUserById() {
//        userRepository.updateUserById();
//    }

}
