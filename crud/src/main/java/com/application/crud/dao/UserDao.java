package com.application.crud.dao;
import com.application.crud.model.User;
import java.util.List;

public interface UserDao {
    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, byte age, String email);

    void removeUserById(long id);

    public User getUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
