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
import model.Cart;

/**
 *
 * @author Hieu
 */
public class CartDAO extends DBContext {

    public List<Cart> getCartByUserId(int user_id) {
        List<Cart> listC = new ArrayList<>();
        String sql = "select id, user_id, product_id, quantity, added_at\n"
                + "from carts where user_id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart a = new Cart();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setQuantity(rs.getInt("quantity"));
                a.setAdded(rs.getTimestamp("added_at"));
                listC.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listC;
    }
    
    public Cart getCartByUserAndProduct(int user_id, int product_id) {
        String sql = "select id, user_id, product_id, quantity, added_at\n"
                + "from carts where user_id = ? and product_id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, product_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cart a = new Cart();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setQuantity(rs.getInt("quantity"));
                a.setAdded(rs.getTimestamp("added_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void newCart(int user_id, int product_id, int quantity) {
        String sql = "INSERT INTO Carts (user_id, product_id, quantity) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, product_id);
            st.setInt(3, quantity);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateCart(int id, int quantity) {
        String sql = "update carts\n" + 
                     "set quantity = ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, quantity);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteCart(int id) {
        String sql = "DELETE FROM carts WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteAllUserCart(int user_id) {
        String sql = "DELETE FROM carts WHERE user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
