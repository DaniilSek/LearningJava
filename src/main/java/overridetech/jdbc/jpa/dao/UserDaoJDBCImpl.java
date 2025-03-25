package overridetech.jdbc.jpa.dao;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection = Util.getConnection();

    public void createUsersTable() {
        String sql = """
                      CREATE TABLE IF NOT EXISTS Users (
                          id INTEGER PRIMARY KEY,
                          name TEXT NOT NULL,
                          lastName TEXT NOT NULL,
                          age INT
                      );
                      """;
        executeSql(sql);
        System.out.println("Таблица Users создана");
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Users;";
        executeSql(sql);
        System.out.println("Таблица Users удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format(
                "INSERT INTO Users(name, lastName, age) VALUES('%s', '%s' ,%d);",
                name, lastName, age
        );
        executeSql(sql);
        System.out.println("User с именем " + name + " добавлен в Базу данных");
    }

    public void removeUserById(long id) {
        String sql = String.format("DELETE FROM Users WHERE id=%d;", id);
        executeSql(sql);
        System.out.println("User с id " + id + " удален из Базы данных");
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM Users;";
        List<User> users = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                users.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM Users;";
        executeSql(sql);
        System.out.println("Таблица Users очищена");
    }

    /**
     * Метод для выполнения SQL запроса
     */
    private void executeSql(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
