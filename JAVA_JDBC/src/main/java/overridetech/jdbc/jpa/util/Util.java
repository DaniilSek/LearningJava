package overridetech.jdbc.jpa.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class Util {

    // реализуйте настройку соеденения с БД
    private static final String DB_URL = "jdbc:sqlite::memory:";

    /**
     * Метод для создания соединения с базой данных
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory setupSessionFactory() {
        // Создаем StandardServiceRegistry для настройки параметров Hibernate
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.sqlite.JDBC") // класс драйвера JDBC
                .applySetting("hibernate.connection.url", DB_URL) // URL подключения к базе данных
                .applySetting("hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect") // диалект Hibernate, соответствующий используемой базе данных
                .applySetting("hibernate.show_sql", true) // настройка для включения/выключения логирования SQL-запросов
                .applySetting("hibernate.hbm2ddl.auto", "none")
                //.applySetting("hibernate.hbm2ddl.auto", "update") // настройка для автоматического создания и обновления схем базы данных
                .build();

        try {
            // Создаем SessionFactory
            SessionFactory sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(overridetech.jdbc.jpa.model.User.class) // Указываем классы, которые будут управляться Hibernate
                    .buildMetadata()
                    .buildSessionFactory();
            return sessionFactory;
        } catch (Exception e) {
            // Утилизация StandardServiceRegistry при возникновении ошибки
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }
}
