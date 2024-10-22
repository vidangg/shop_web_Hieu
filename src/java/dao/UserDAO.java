/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author Hieu
 */
public class UserDAO extends DBContext {

    public List<User> getAll() {
        List<User> listU = new ArrayList<>();
        String sql = "select id, username, password, email, fullname, money, role, created_at\n"
                + "from users\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setEmail(rs.getString("email"));
                a.setFullname(rs.getString("fullname"));
                a.setMoney(rs.getBigDecimal("money"));
                a.setRole(rs.getString("role"));
                a.setCreated(rs.getTimestamp("created_at"));
                listU.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listU;
    }
    
    public User getUserById(int id) {
        String sql = "select id, username, password, email, fullname, money, role, created_at\n" + 
                     "from users where id= ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setEmail(rs.getString("email"));
                a.setFullname(rs.getString("fullname"));
                a.setMoney(rs.getBigDecimal("money"));
                a.setRole(rs.getString("role"));
                a.setCreated(rs.getTimestamp("created_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void register(String username, String password, String email, String fullname) {
        String sql = "INSERT INTO Users (username, password, email, fullname) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, fullname);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public User login(String username, String password) {
        String sql = "select id, username, password, email, fullname, money, role, created_at\n"
                + " from Users where Username = ? and Password = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User a = new User();
                a.setId(rs.getInt("id"));
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));
                a.setEmail(rs.getString("email"));
                a.setFullname(rs.getString("fullname"));
                a.setMoney(rs.getBigDecimal("money"));
                a.setRole(rs.getString("role"));
                a.setCreated(rs.getTimestamp("created_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean checkExistedUser(String username, String email) {
        String sql = "SELECT Username, Email FROM Users";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                if (username.equals(rs.getString("username"))) {
                    return true;
                }
                if (email.equals(rs.getString("email"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public void updateUser(User user) {
        String sql = "update Users\n" + 
                     "set password = ?, email = ?, fullname = ?, money = ?, role = ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user.getPassword());
            st.setString(2, user.getEmail());
            st.setString(3, user.getFullname());
            st.setBigDecimal(4, user.getMoney());
            st.setString(5, user.getRole());
            st.setInt(6, user.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateUserMoney(int id, BigDecimal money) {
        String sql = "update Users\n" + 
                     "set money = money - ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setBigDecimal(1, money);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void newUser(String username, String password, String email, String fullname, BigDecimal money, String role) {
        String sql = "INSERT INTO Users (username, password, email, money, fullname, role) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            st.setBigDecimal(4, money);
            st.setString(5, fullname);
            st.setString(6, role);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteUser(int id) {
        String sql = "DELETE FROM Users WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
