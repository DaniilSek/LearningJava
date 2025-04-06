package com.application.crud.dao;

import com.application.crud.model.User;
import com.application.crud.util.Util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoHibernate implements UserDao {

    private final SessionFactory sessionFactory = Util.setupSessionFactory();

    public UserDaoHibernate() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createNativeQuery("CREATE TABLE " +
                    "IF NOT EXISTS Users (id INTEGER PRIMARY KEY, " +
                    "name TEXT NOT NULL, " +
                    "lastName TEXT NOT NULL, " +
                    "age INT)");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users создана");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createNativeQuery("DROP TABLE IF EXISTS Users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users удалена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, byte age, String email) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, age, email);
            session.save(user);
            transaction.commit();
            System.out.println("User с именем " + name + " добавлен в Базу данных");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.load(User.class, id);
            if (user != null) {
                session.delete(user);
                System.out.println("User с id " + id + " удален из Базы данных");
            }
            else {
                System.out.println("User с id " + id + " не существует в Базе данных");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserById(long id) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            user = session.load(User.class, id);
            if (user != null) {
                System.out.println("User с id " + id + " ");
            }
            else {
                System.out.println("User с id " + id + " не существует в Базе данных");
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
