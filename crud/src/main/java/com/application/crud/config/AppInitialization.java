// package com.application.crud.config;
//
// import com.application.crud.model.Role;
// import com.application.crud.model.User;
// import com.application.crud.repositories.RoleRepository;
// import com.application.crud.repositories.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
// import java.util.Collections;
// import java.util.List;
// import java.util.Set;
//
// @Configuration
// public class AppInitialization {
//
//     @Autowired
//     private UserRepository userRepository;
//
//     @Autowired
//     private RoleRepository roleRepository;
//
// //    @Autowired
// //    private BCryptPasswordEncoder passwordEncoder;
//
//     @Bean
//     CommandLineRunner initializeDatabase() {
//         return args -> {
//             // Проверяем, есть ли пользователь с ролью ADMIN
//             if (userRepository.findByRolesContaining(new Role("ROLE_ADMIN")).isEmpty()) {
//                 // Создаем нового пользователя с ролью ADMIN
//                 User admin = new User();
//                 admin.setEmail("admin@yandex.ru");
//                 admin.setPassword("admin");
//                 admin.setName("Admin");
//                 // Находим роль ADMIN и назначаем её пользователю
//                 Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
//                 admin.setRoles(Set.of(adminRole));
//                 userRepository.save(admin);
//             }
//             else {
//                 List<User> admins = userRepository.findByRolesContaining(new Role("ROLE_ADMIN"));
//                 admins.forEach(System.out::println);
//             }
//         };
//     }
// }