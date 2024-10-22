/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 *
 * @author Hieu
 */
public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String image_url;
    private int category_id;
    private String type;
    private Timestamp created;
    private Timestamp updated;

    public Product() {
    }

    public Product(int id, String name, String description, double price, int stock, String image_url, int category_id, String type, Timestamp created, Timestamp updated) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.image_url = image_url;
        this.category_id = category_id;
        this.type = type;
        this.created = created;
        this.updated = updated;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        return vnFormat.format(price);
    }
    
    public double getNumberPrice() {
        return price;
    }
    
    public BigDecimal getBigPrice() {
        return new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreated() {
        LocalDateTime dateTime = created.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getUpdated() {
        LocalDateTime dateTime = updated.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }
}
