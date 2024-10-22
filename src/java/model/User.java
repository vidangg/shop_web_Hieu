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
import java.util.regex.Pattern;

/**
 *
 * @author Hieu
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String fullname;
    private BigDecimal money;
    private String role;
    private Timestamp created;

    public User() {
    }

    public User(int id, String username, String email, String password, String fullname, BigDecimal money, String role, Timestamp created) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.money = money;
        this.role = role;
        this.created = created;
    }

    public User(int id, String username, String email, String password, String fullname, BigDecimal money, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.money = money;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public BigDecimal getMoney() {
        return money;
    }
    
    public String getFormatMoney() {
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
        return vnFormat.format(money);
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreated() {
        LocalDateTime dateTime = created.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a dd-MM-yyyy");
        return dateTime.format(formatter);
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
    
    public static boolean isStrongPassword(String password) {
        // Kiểm tra độ dài của mật khẩu (ít nhất 8 ký tự)
        if (password.length() < 8) {
            return false;
        }
        // Kiểm tra sự hiện diện của ít nhất một ký tự chữ cái
        if (!Pattern.compile("[a-zA-Z]").matcher(password).find()) {
            return false;
        }
        // Kiểm tra sự hiện diện của ít nhất một ký tự số
        if (!Pattern.compile("\\d").matcher(password).find()) {
            return false;
        }
        // Kiểm tra sự hiện diện của ít nhất một ký tự đặc biệt
        if (!Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find()) {
            return false;
        }
        // Nếu mọi tiêu chí đều đáp ứng, mật khẩu được coi là đủ mạnh
        return true;
    }
    
    public static boolean isValidUsername(String username) {
        // Regular expression to check if username contains only letters and numbers
        String regex = "^[a-zA-Z0-9]+$";
        return username != null && username.matches(regex);
    }
}
