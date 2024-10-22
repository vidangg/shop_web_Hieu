<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng nhập</title>
        <link type="text/css" rel="stylesheet" href="css/start.css">
        <link type="text/css" rel="stylesheet" href="css/login.css">
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="login-container">
            <h2>Đăng nhập</h2>
            <form action="login" method="POST">
                <input type="text" name="username" placeholder="Username" required>
                <input type="password" name="password" placeholder="Password" required>
                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                        <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %> 
                <button type="submit">Đăng nhập</button>
                <p>Chưa có tài khoản? <a href="register">Register</a></p>
            </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
