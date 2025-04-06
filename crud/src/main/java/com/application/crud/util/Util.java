package com.application.crud.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Util {

    private static final String DB_URL = "jdbc:sqlite::memory:";

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
                    .addAnnotatedClass(com.application.crud.model.User.class) // Указываем классы, которые будут управляться Hibernate
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