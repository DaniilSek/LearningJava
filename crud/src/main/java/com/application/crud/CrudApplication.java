package com.application.crud;

import com.application.crud.model.Role;
import com.application.crud.model.User;
import com.application.crud.repositories.RoleRepository;
import com.application.crud.repositories.UserRepository;
import com.application.crud.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Set;

@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository, RoleRepository roleRepository) {
		return args -> {

			// Создаем роли, если их нет
			Role adminRole = roleRepository.findByRole("ROLE_ADMIN");
			if (adminRole == null) {
				adminRole = new Role("ROLE_ADMIN");
				roleRepository.save(adminRole);
			}

			Role userRole = roleRepository.findByRole("ROLE_USER");
			if (userRole == null) {
				userRole = new Role("ROLE_USER");
				roleRepository.save(userRole);
			}

			// Создаем администратора
			if (userRepository.findByEmail("admin@yandex.ru").isEmpty()) {
				User admin = new User();
				admin.setEmail("admin@yandex.ru");
				admin.setPassword("admin");
				admin.setName("Admin");

				// Получаем роль из текущей сессии
				Role persistedAdminRole = roleRepository.findById(adminRole.getId()).get();
				admin.setRoles(Set.of(persistedAdminRole));

				userRepository.save(admin);
				System.out.println("Admin user created!");
			}
			else {
				System.out.println("Admin (admin@yandex.ru) already exists");
			}
		};
	}
}
