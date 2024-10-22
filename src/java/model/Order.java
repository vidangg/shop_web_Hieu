/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Hieu
 */
public class Order {
    private int id;
    private int user_id;
    private double total;
    private String status;
    private Timestamp created;

    public Order() {
    }

    public Order(int id, int user_id, double total, String status, Timestamp created) {
        this.id = id;
        this.user_id = user_id;
        this.total = total;
        this.status = status;
        this.created = created;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated() {
        LocalDateTime dateTime = created.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    
}
