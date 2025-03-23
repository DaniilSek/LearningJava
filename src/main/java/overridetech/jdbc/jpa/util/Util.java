package overridetech.jdbc.jpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {

    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;

    private static void setupSessionFactory() {
        // Создаем службу регистрации Hibernate
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.connection.driver_class", "org.postgresql.Driver") // класс драйвера JDBC
                .applySetting("hibernate.connection.url", "jdbc:postgresql://localhost:5432/database_name") // URL подключения к базе данных
                .applySetting("hibernate.connection.username", "username")
                .applySetting("hibernate.connection.password", "password")
                .applySetting("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect") // диалект Hibernate, соответствующий используемой базе данных
                .applySetting("hibernate.show_sql", true) // настройка для включения/выключения логирования SQL-запросов
                .applySetting("hibernate.hbm2ddl.auto", "update") // настройка для автоматического создания и обновления схем базы данных
                .build();

        try {
            // Создаем фабрику сессий
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(overridetech.jdbc.jpa.model.User.class) // Указываем классы, которые будут управляться Hibernate
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            // Destroy службы регистрации при возникновении ошибки
            StandardServiceRegistryBuilder.destroy(registry);
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
