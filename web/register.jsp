<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Đăng ký</title>
        <link type="text/css" rel="stylesheet" href="css/start.css">
        <link type="text/css" rel="stylesheet" href="css/register.css">
    </head>
    <body>
        <%@include file="header.jsp" %>
        <div class="register-container">
            <h2>Đăng ký</h2>
            <form action="register" method="POST">
                <input type="text" name="username" placeholder="Username" required>
                <input type="text" name="fullname" placeholder="Fullname" required>
                <input type="email" name="email" placeholder="Email" required>
                <input type="password" name="password" placeholder="Password" required>
                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                        <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %>
                <button type="submit">Đăng ký</button>
                <p>Đã có tài khoản? <a href="login">Login</a></p>
            </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
