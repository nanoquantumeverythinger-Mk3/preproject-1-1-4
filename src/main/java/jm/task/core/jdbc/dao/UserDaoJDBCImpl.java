package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection conn = Util.getConnection()) {
            Statement s = conn.createStatement();
            try {
                s.executeUpdate("CREATE TABLE IF NOT EXISTS users (Id bigint AUTO_INCREMENT PRIMARY KEY, Name varchar(255), Lastname varchar(255), Age tinyint);");
                conn.commit();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection conn = Util.getConnection()) {
            Statement s = conn.createStatement();
            try {
                s.executeUpdate("DROP TABLE IF EXISTS users;");
                conn.commit();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getConnection()) {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO users (Name, Lastname, Age) VALUES (?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, lastName);
                ps.setByte(3, age);
                ps.executeUpdate();
                conn.commit();
                ps.close();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM users WHERE Id = ?;")) {
            try {
                ps.setLong(1, id);
                ps.executeUpdate();
                conn.commit();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new LinkedList<>();
        try (Connection conn = Util.getConnection();
             Statement s = conn.createStatement()) {
            ResultSet rs = null;
            try {
                rs = s.executeQuery("SELECT * FROM Users");
                conn.commit();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
            while (rs.next()) {
                list.add(new User(rs.getString(2), rs.getString(3), rs.getByte(4)));
                list.get(list.size() - 1).setId(rs.getLong(1));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getConnection()) {
            Statement s = conn.createStatement();
            try {
                s.executeUpdate("TRUNCATE TABLE users;");
                conn.commit();
            } catch (SQLException SQLCommit) {
                conn.rollback();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
