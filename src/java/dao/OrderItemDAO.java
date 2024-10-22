/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order_item;

/**
 *
 * @author Hieu
 */
public class OrderItemDAO extends DBContext {
    
    public List<Order_item> getOrderItemByOrderId(int order_id) {
        List<Order_item> listO = new ArrayList<>();
        String sql = "select id, order_id, product_id, quantity, price\n" + 
                     "from order_items where order_id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, order_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order_item a = new Order_item();
                a.setId(rs.getInt("id"));
                a.setOrder_id(rs.getInt("order_id"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setQuantity(rs.getInt("quantity"));
                a.setPrice(rs.getDouble("price"));
                listO.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listO;
    }
    
    public boolean isOrderItemByProduct(int product_id) {
        List<Order_item> listO = new ArrayList<>();
        String sql = "select id, order_id, product_id, quantity, price\n" + 
                     "from order_items where product_id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, product_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order_item a = new Order_item();
                a.setId(rs.getInt("id"));
                a.setOrder_id(rs.getInt("order_id"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setQuantity(rs.getInt("quantity"));
                a.setPrice(rs.getDouble("price"));
                listO.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listO.size() > 0;
    }
    
    public void deleteOrderItemByOrderId(int order_id) {
        String sql = "DELETE FROM order_items WHERE order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, order_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void newOrderItem(int order_id, int product_id, int quantity, double price) {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, order_id);
            st.setInt(2, product_id);
            st.setInt(3, quantity);
            st.setDouble(4, price);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

