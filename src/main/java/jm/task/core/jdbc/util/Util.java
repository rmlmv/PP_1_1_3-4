package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private final static String DRIVER = "com.mysql.jdbc.Driver";
    private final static String URL = "jdbc:mysql://localhost:3306/pp_1_db";
    private final static String USERNAME = "bestuser";
    private final static String PASSWORD = "bestuser";

    private final static String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private final static boolean SHOW_SQL = true;
    private final static String CURRENT_SESSION_CONTEXT_CLASS = "thread";

    private static Connection connection;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Properties properties = new Properties();

                properties.put(Environment.JAKARTA_JDBC_DRIVER, DRIVER);
                properties.put(Environment.JAKARTA_JDBC_URL, URL);
                properties.put(Environment.JAKARTA_JDBC_USER, USERNAME);
                properties.put(Environment.JAKARTA_JDBC_PASSWORD, PASSWORD);

                properties.put(Environment.DIALECT, DIALECT);
                properties.put(Environment.SHOW_SQL, SHOW_SQL);
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, CURRENT_SESSION_CONTEXT_CLASS);

                sessionFactory = new Configuration()
                        .setProperties(properties)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}