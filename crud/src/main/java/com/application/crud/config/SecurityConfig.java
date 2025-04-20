package com.application.crud.config;

import com.application.crud.repositories.RoleRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final RoleRepository roleRepository;

    public SecurityConfig(UserDetailsService userDetailsService,
                          RoleRepository roleRepository) {
        this.userDetailsService = userDetailsService;
        this.roleRepository = roleRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/users/users_page", "/users/auth/registration", "/**").permitAll()
//                        .requestMatchers("/users/admin/**").permitAll()//.hasAuthority("ADMIN") // Доступ только для администраторов
//                        //.anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults());
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/users/edit_user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/users_page").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/users/edit_user")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }
}