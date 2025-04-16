package com.application.crud.repositories;
import com.application.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRolesContaining(String admin);
}
