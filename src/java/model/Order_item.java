/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Hieu
 */
public class Order_item {
    private int id;
    private int order_id;
    private int product_id;
    private int quantity;
    private double price;

    public Order_item() {
    }

    public Order_item(int id, int order_id, int product_id, int quantity, double price) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        return vnFormat.format(price);
    }
    
    public double getNumberPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    
}
