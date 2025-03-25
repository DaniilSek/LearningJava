package overridetech.jdbc.jpa.service;

import overridetech.jdbc.jpa.dao.UserDao;
import overridetech.jdbc.jpa.dao.UserDaoJDBCImpl;
import overridetech.jdbc.jpa.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private static UserDao user = null;

    // Заглушка для теста
    public UserServiceImpl() {
        user = new UserDaoJDBCImpl();
    }

    public UserServiceImpl(UserDao user) {
        this.user = user;
    }

    public void createUsersTable() {
        user.createUsersTable();

    }

    public void dropUsersTable() {
        user.dropUsersTable();

    }

    public void saveUser(String name, String lastName, byte age) {
        user.saveUser(name, lastName, age);

    }

    public void removeUserById(long id) {
        user.removeUserById(id);

    }

    public List<User> getAllUsers() {
        return user.getAllUsers();
    }

    public void cleanUsersTable() {
        user.cleanUsersTable();
    }
}
