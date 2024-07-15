package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        final String createUsersTableQuery = """
                CREATE TABLE IF NOT EXISTS `users` (
                `id` BIGINT NOT NULL AUTO_INCREMENT,
                `name` VARCHAR(45) NULL,
                `last_name` VARCHAR(45) NULL,
                `age` TINYINT NULL,
                PRIMARY KEY (`id`),
                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
                """;

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createNativeQuery(createUsersTableQuery).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        final String dropUsersTableQuery = "DROP TABLE IF EXISTS `users`";

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.createNativeQuery(dropUsersTableQuery).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();

            System.out.println("User " + user + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            User user = session.get(User.class, id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            users.addAll(session.createQuery("from User", User.class).getResultList());

            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Query query = session.createQuery("delete User");
            query.executeUpdate();

            session.getTransaction().commit();
        }  catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
