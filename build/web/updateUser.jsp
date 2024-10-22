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
        <link type="text/css" rel="stylesheet" href="css/upUser.css">
    </head>
    <body>
        <%
            User user = (User) request.getAttribute("user");
        %>
        <%@include file="header.jsp" %>
        <div class="profile-container">
            <h2>Thông tin người dùng</h2>
            <form name="f" action="update?action=user" method="POST">
                <input type="hidden" id="id" name="id" value="<%=user.getId()%>">
                
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%=user.getUsername()%>" readonly>
                
                <label for="fullname">Full Name:</label>
                <input type="text" id="fullname" name="fullname" value="<%=user.getFullname()%>" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" value="<%=user.getEmail()%>" required>
                
                <label for="money">Money:</label>
                <input type="number" id="money" name="money" step="0.01" value="<%=user.getMoney()%>" required>

                <label for="role">Role</label>
                <select id="role" name="role">
                    <%
                        if(user.getRole().equals("admin")) {
                    %>
                            <option value="admin" selected="selected">Admin</option>
                            <option value="customer">Customer</option>
                    <% 
                        } else {
                    %>
                            <option value="admin">Admin</option>
                            <option value="customer" selected="selected">Customer</option>
                    <%
                        }
                    %>
                </select>
                
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" value="<%=user.getPassword()%>" required>
                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %>
                <button style="background-color: #ff9900;" type="submit">Lưu</button>
                <button style="margin-left: 25px; background-color: #007185;" onclick="deleteUser(event)" type="submit">Xóa</button>
            </form>
        </div>
        <%@include file="footer.jsp" %>
    </body>
    
    <script>
        function deleteUser(event) {
            if (${not empty user}) {
                event.preventDefault();
                document.f.action = "delete?action=user";
                document.f.submit();
            } else {
                window.location.href = 'login';
            }
        }
    </script>
</html>
