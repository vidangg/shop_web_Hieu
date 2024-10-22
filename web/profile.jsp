<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Thông tin cá nhân</title>
        <link type="text/css" rel="stylesheet" href="css/start.css">
        <link type="text/css" rel="stylesheet" href="css/profile.css">
    </head>
    <body>
        <c:set var="ac" value="${sessionScope.user}"/>
        <%@include file="header.jsp" %>
        <div class="profile-container">
            <h2>Thông tin cá nhân</h2>
            <form action="profile" method="POST">
                <label for="username">Username:</label>
                <input type="text" id="username" value="${ac.getUsername()}" readonly>
                
                <label for="fullname">Full Name:</label>
                <input type="text" id="fullname" name="fullname" value="${ac.getFullname()}" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="${ac.getEmail()}" required>
                
                <label for="email">Money:</label>
                <input type="text" value="${ac.getFormatMoney()}" readonly>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="${ac.getPassword()}" required>
                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %> 
                <button type="submit">Lưu</button>
            </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
</html>
