package overridetech.jdbc.jpa.dao;

import overridetech.jdbc.jpa.model.User;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import overridetech.jdbc.jpa.util.Util;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.setupSessionFactory();

    public UserDaoHibernateImpl() {

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
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
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
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM Users", User.class);
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
            Query query = session.createQuery("DELETE FROM Users");
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица Users очищена");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
