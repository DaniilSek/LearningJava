package com.application.crud.services;

import com.application.crud.model.Role;
import com.application.crud.model.User;
import com.application.crud.repositories.RoleRepository;
import com.application.crud.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        Role userRole = roleRepository.findByRole("ROLE_USER");
        Role persistedUserRole = roleRepository.findById(userRole.getId()).get();
        if (user.getAuthorities().isEmpty()) {
            user.setRoles(Set.of(persistedUserRole));
        }
        //user.setRoles(Collections.singleton(userRole));
        if (user.getPassword() != null && !user.getPassword().startsWith("$2a$")) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByName(String name) {
        if (userRepository.findByName(name) == null) {
            return null;
        }
        return userRepository.findByName(name);
    }

}
