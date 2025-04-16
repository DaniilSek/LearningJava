package com.application.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/users_page", "/users/auth/registration").permitAll()
                        .requestMatchers("/users/admin/**").permitAll()//.hasAuthority("ADMIN") // Доступ только для администраторов
                        //.anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user =
                User.builder()
                        .username("user")
                        .password("user") // Без шифрования пароля
                        .authorities("ROLE_USER")
                        .build();
        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password("admin") // Без шифрования пароля
                        .authorities("ROLE_ADMIN", "ROLE_USER") // Несколько ролей
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}

// package com.application.crud.config;

// import com.application.crud.model.User;
// import com.application.crud.repositories.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import java.util.Collections;
// import java.util.List;

// @Configuration
// public class AppInitialization {

//     @Autowired
//     private UserRepository userRepository;

// //    @Autowired
// //    private BCryptPasswordEncoder passwordEncoder;

//     @Bean
//     CommandLineRunner initializeDatabase() {
//         return args -> {
//             // Проверяем, есть ли пользователь с ролью ADMIN
//             if (userRepository.findByRolesContaining("ROLE_ADMIN").isEmpty()) {
//                 // Создаем нового пользователя с ролью ADMIN
//                 User admin = new User();
//                 admin.setName("admin");
//                 //admin.setPassword(passwordEncoder.encode("admin"));
//                 admin.setPassword("admin");
//                 admin.setEmail("admin@boss.ru");
//                 admin.setRoles(Collections.singleton("ROLE_ADMIN"));

//                 // Сохраняем пользователя в базу данных
//                 userRepository.save(admin);
//             }
//             else {
//                 List<User> admins = userRepository.findByRolesContaining("ROLE_ADMIN");
//                 admins.forEach(System.out::println);
//             }
//         };
//     }
// }
