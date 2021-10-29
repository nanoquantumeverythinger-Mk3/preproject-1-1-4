package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    //constants
    static private final String URL = "jdbc:mysql://localhost:3306/my_database?useSSL=false";
    static private final String USER = "root";
    static private final String PASSWORD = "password";
    static private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //jdbc
    static private Connection conn;
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    //hibernate
    static SessionFactory sessionFactory;
    static {
        Properties prop = new Properties();
        /*
        prop.put(Environment.URL, URL);
        prop.put(Environment.USER, USER);
        prop.put(Environment.PASS, PASSWORD);
        prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        prop.put(Environment.DRIVER, DRIVER);
        */
        prop.setProperty("hibernate.connection.url", URL);
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        prop.setProperty("hibernate.connection.username", USER);
        prop.setProperty("hibernate.connection.password", PASSWORD);
        prop.setProperty("hibernate.connection.driver_class", DRIVER);

        sessionFactory = new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
