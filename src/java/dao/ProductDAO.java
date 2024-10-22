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
import model.Product;

/**
 *
 * @author Hieu
 */
public class ProductDAO extends DBContext {

    public List<Product> getAll() {
        List<Product> listP = new ArrayList<>();
        String sql = "select id, name, description, price, stock, image_url, category_id, type, created_at, updated_at\n"
                + "from products\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setType(rs.getString("type"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                listP.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listP;
    }
    
    public List<Product> getProduct() {
        List<Product> listP = new ArrayList<>();
        String sql = "select id, name, description, price, stock, image_url, category_id, created_at, updated_at\n"
                + "from products where stock > 0 and type = 'appear'\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                listP.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listP;
    }
    
    public List<Product> getProductByCate(int cateId) {
        List<Product> listP = new ArrayList<>();
        String sql = "select id, name, description, price, stock, image_url, category_id, created_at, updated_at\n"
                + "from products where category_id = ? and stock > 0 and type = 'appear'\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cateId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                listP.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listP;
    }
    
    public List<Product> getProductByName(String name) {
        List<Product> listP = new ArrayList<>();
        String sql = "select id, name, description, price, stock, image_url, category_id, created_at, updated_at\n" +
                     "from products where name LIKE ? and stock > 0 and type = 'appear'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                listP.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listP;
    }
    
    public List<Product> getProductByNameAndCate(String name, int cateId) {
        List<Product> listP = new ArrayList<>();
        String sql = "select id, name, description, price, stock, image_url, category_id, created_at, updated_at\n" +
                     "from products where name LIKE ? and category_id = ? and stock > 0 and type = 'appear'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "%" + name + "%");
            st.setInt(2, cateId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                listP.add(a);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listP;
    }
    
    public Product getProductById(int id) {
        String sql = "select id, name, description, price, stock, image_url, category_id, type, created_at, updated_at\n" +
                     "from products where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product a = new Product();
                a.setId(rs.getInt("id"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                a.setPrice(rs.getDouble("price"));
                a.setStock(rs.getInt("stock"));
                a.setImage_url(rs.getString("image_url"));
                a.setCategory_id(rs.getInt("category_id"));
                a.setType(rs.getString("type"));
                a.setCreated(rs.getTimestamp("created_at"));
                a.setUpdated(rs.getTimestamp("updated_at"));
                return a;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    
    public void newProduct(String name, String description, double price, int stock, String image_url, int category_id, String type) {
        String sql = "INSERT INTO products (name, description, price, stock, image_url, category_id, type) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, description);
            st.setDouble(3, price);
            st.setInt(4, stock);
            st.setString(5, image_url);
            st.setInt(6, category_id);
            st.setString(7, type);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateProduct(int id, String name, String description, double price, int stock, String image_url, int category_id, String type) {
        String sql = "update products\n" + 
                     "set name = ?, description = ?, price = ?, stock = ?, image_url = ?, category_id = ?, type = ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, description);
            st.setDouble(3, price);
            st.setInt(4, stock);
            st.setString(5, image_url);
            st.setInt(6, category_id);
            st.setString(7, type);
            st.setInt(8, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateStock(int id, int stock) {
        String sql = "update products\n" + 
                     "set stock = stock + ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, stock);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void updateType(int id, String type) {
        String sql = "update products\n" + 
                     "set type = ?\n" + 
                     "where id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, type);
            st.setInt(2, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
