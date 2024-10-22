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
public class Comment {
    private int id;
    private int user_id;
    private int product_id;
    private String comment;
    private Timestamp created;

    public Comment() {
    }

    public Comment(int id, int user_id, int product_id, String comment, Timestamp created) {
        this.id = id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.comment = comment;
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

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated() {
        LocalDateTime dateTime = created.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
