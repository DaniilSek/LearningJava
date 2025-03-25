package overridetech.jdbc.jpa;

import overridetech.jdbc.jpa.dao.UserDaoHibernateImpl;
import overridetech.jdbc.jpa.dao.UserDaoJDBCImpl;
import overridetech.jdbc.jpa.service.UserServiceImpl;


public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl userServiceImpl;

        // Создаем экземпляр UserDaoJDBCImpl для UserServiceImpl
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

        userServiceImpl = new UserServiceImpl(userDaoJDBC);

        // Создание таблицы Users, если она ещё не существует
        userServiceImpl.createUsersTable();

        // Добавление 4-х юзеров
        userServiceImpl.saveUser("Евгений", "Яковлев", (byte) 28);
        userServiceImpl.saveUser("Сергей", "Канке", (byte) 27);
        userServiceImpl.saveUser("Секлецов", "Даниил", (byte) 28);
        userServiceImpl.saveUser("Арина", "Мезенина", (byte) 23);

        // Получение всех юзеров из Базы данных
        userServiceImpl.getAllUsers().forEach(System.out::println);

        // Очистка таблицы Users
        userServiceImpl.cleanUsersTable();

        // Удаление таблицы Users
        userServiceImpl.dropUsersTable();

        // Создаем экземпляр UserDaoHibernateImpl для UserServiceImpl
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();

        userServiceImpl = new UserServiceImpl(userDaoHibernate);

        // Создаем таблицу Users
        userDaoHibernate.createUsersTable();

        // Добавление 4-х юзеров
        userServiceImpl.saveUser("Евгений", "Яковлев", (byte) 28);
        userServiceImpl.saveUser("Сергей", "Канке", (byte) 27);
        userServiceImpl.saveUser("Секлецов", "Даниил", (byte) 28);
        userServiceImpl.saveUser("Арина", "Мезенина", (byte) 23);

        // Получение всех юзеров из Базы данных
        userServiceImpl.getAllUsers().forEach(System.out::println);

        // Очистка таблицы Users
        userServiceImpl.cleanUsersTable();

        // Удаление таблицы Users
        userServiceImpl.dropUsersTable();

        System.out.println();

    }

}
