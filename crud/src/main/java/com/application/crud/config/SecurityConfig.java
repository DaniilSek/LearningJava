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
                        .requestMatchers("/", "/register").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") // Доступ только для администраторов
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
                        .password("{noop}password") // Без шифрования пароля
                        .authorities("ROLE_USER")
                        .build();
        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password("{noop}adminpass") // Без шифрования пароля
                        .authorities("ROLE_ADMIN", "ROLE_USER") // Несколько ролей
                        .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
}