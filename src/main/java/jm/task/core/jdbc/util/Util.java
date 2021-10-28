package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    //jdbc
    static private String url = "jdbc:mysql://localhost:3306/my_database?useSSL=false";
    static private String user = "root";
    static private String password = "password";
    static private Connection conn;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return conn = DriverManager.getConnection(url, user, password);
    }

    //hibernate
    static SessionFactory sessionFactory;

    static {
        Properties prop = new Properties();
        /*prop.put(Environment.URL, "jdbc:mysql://localhost:3306/my_database?useSSL=false");
        prop.put(Environment.USER, "root");
        prop.put(Environment.PASS, "password");
        prop.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        prop.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        */
        prop.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/my_database?serverTimezone=UTC");
        prop.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        prop.setProperty("hibernate.connection.username", "root");
        prop.setProperty("hibernate.connection.password", "password");
        prop.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        sessionFactory = new Configuration().addProperties(prop).addAnnotatedClass(User.class).buildSessionFactory();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
