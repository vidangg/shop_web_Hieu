/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author QuocCuong
 */
public class DBContext {
    protected Connection connection;
    public DBContext() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties connectionProps = new Properties();
            connectionProps.put("user", "root");
            connectionProps.put("password", "12345678");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mobile_store", connectionProps);
        } catch(ClassNotFoundException | SQLException e) {
            
        }
    }
}
