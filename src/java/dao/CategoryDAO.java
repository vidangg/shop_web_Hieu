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
import model.Category;

/**
 *
 * @author Hieu
 */
public class CategoryDAO extends DBContext {
    
    public List<Category> getAll() {
        List<Category> listC = new ArrayList<>();
        String sql = "select id, name, created_at\n"
                + "from categories\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category a = new Category();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setCreated(rs.getTimestamp("created_at"));
                listC.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listC;
    }
    
    public Category getCategoryById(int id) {
        String sql = "select id, name, created_at\n" +
                     "from categories where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Category a = new Category();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setCreated(rs.getTimestamp("created_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
