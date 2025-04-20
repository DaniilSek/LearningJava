package com.application.crud.repositories;
import com.application.crud.model.Role;
import com.application.crud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRolesContaining(Role admin);

    User findByName(String name);

    Optional<User> findByEmail(String email);

}
