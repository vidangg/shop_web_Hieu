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
import model.Comment;

/**
 *
 * @author Hieu
 */
public class CommentDAO extends DBContext {
    public List<Comment> getCommentByProduct(int product_id) {
        List<Comment> listC = new ArrayList<>();
        String sql = "select id, user_id, product_id, comment, created_at\n"
                + "from comments where product_id = ?\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, product_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Comment a = new Comment();
                a.setId(rs.getInt("id"));
                a.setUser_id(rs.getInt("user_id"));
                a.setProduct_id(rs.getInt("product_id"));
                a.setComment(rs.getString("comment"));
                a.setCreated(rs.getTimestamp("created_at"));
                listC.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listC;
    }
    
    public void newComment(int user_id, int product_id, String comment) {
        String sql = "INSERT INTO Comments (user_id, product_id, comment) VALUES (?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.setInt(2, product_id);
            st.setString(3, comment);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteComment(int id) {
        String sql = "DELETE FROM Comments WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteCommentByUserId(int user_id) {
        String sql = "DELETE FROM Comments WHERE user_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, user_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteCommentByProductId(int product_id) {
        String sql = "DELETE FROM Comments WHERE product_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, product_id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
