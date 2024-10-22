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
import model.Order;

/**
 *
 * @author Hieu
 */
public class OrderDAO extends DBContext {
    
    public List<Order> getAll() {
        List<Order> listO = new ArrayList<>();
        String sql = "select id, user_id, total, status, created_at\n" + 
                     "from orders ORDER BY id DESC\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order a = new Order();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setTotal(rs.getInt("total"));
                a.setStatus(rs.getString("status"));
                a.setCreated(rs.getTimestamp("created_at"));
                listO.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listO;
    }
    
    public List<Order> getOrderByUserId(int user_id) {
        List<Order> listO = new ArrayList<>();
        String sql = "select id, user_id, total, status, created_at\n" + 
                     "from orders where user_id = ?\n ORDER BY id DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order a = new Order();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setTotal(rs.getInt("total"));
                a.setStatus(rs.getString("status"));
                a.setCreated(rs.getTimestamp("created_at"));
                listO.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listO;
    }
    
    public Order getOrderById(int id) {
        String sql = "select id, user_id, total, status, created_at\n" + 
                     "from orders where id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order a = new Order();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setTotal(rs.getInt("total"));
                a.setStatus(rs.getString("status"));
                a.setCreated(rs.getTimestamp("created_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void updateOrder(int id, String status) {
        String sql = "update orders\n" + 
                     "set status = ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void newOrder(int user_id, BigDecimal total) {
        String sql = "INSERT INTO orders (user_id, total) VALUES (?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setBigDecimal(2, total);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public Order getNewestUserOrder(int user_id) {
        String sql = "SELECT id, user_id, total, status, created_at\n" +
                     "FROM orders\n" +
                     "WHERE user_id = ?\n" +
                     "ORDER BY created_at DESC\n" +
                     "LIMIT 1;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order a = new Order();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setTotal(rs.getInt("total"));
                a.setStatus(rs.getString("status"));
                a.setCreated(rs.getTimestamp("created_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
