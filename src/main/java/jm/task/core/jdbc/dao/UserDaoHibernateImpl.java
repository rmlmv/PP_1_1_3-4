package jm.task.core.jdbc.dao;

import jakarta.persistence.Query;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
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

        try (Session session = Util.getSession()) {
            session.beginTransaction();

            session.createNativeQuery(createUsersTableQuery).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        final String dropUsersTableQuery = "DROP TABLE IF EXISTS `users`";

        try (Session session = Util.getSession()) {
            session.beginTransaction();

            session.createNativeQuery(dropUsersTableQuery).executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            session.persist(new User(name, lastName, age));

            session.getTransaction().commit();

            System.out.println("User с именем — " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            Query query = session.createQuery("delete from User where id =:userId");
            query.setParameter("userId", id);
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;

        try (Session session = Util.getSession()) {
            session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            session.getTransaction().commit();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSession()) {
            session.beginTransaction();

            Query query = session.createQuery("delete from User");
            query.executeUpdate();

            session.getTransaction().commit();
        }
    }
}
