<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Management</title>
    <link rel="stylesheet" href="css/manageProduct.css">
    <link rel="stylesheet" href="css/start.css">
</head>
<body>
    <%@include file="header.jsp" %>
    
    <div class="mp_container">
        <h1>Quản lý người dùng</h1>
        <button onclick="redirectToAddUser()" id="add-product-btn">Add User</button>
        <table id="product-table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Created</th>
                    <th>View</th>
                </tr>
            </thead>
            <%
                List<User> listU = (List<User>) request.getAttribute("listU");
                if (listU != null) {
                    for (int i = 0; i < listU.size(); i++) {  
            %>
                        <tbody>
                            <td><%=listU.get(i).getId()%></td>
                            <td><%=listU.get(i).getUsername()%></td>
                            <td><%=listU.get(i).getFullname()%></td>
                            <td><%=listU.get(i).getEmail()%></td>
                            <td><%=listU.get(i).getRole()%></td>
                            <td><%=listU.get(i).getCreated()%></td>
                            <td><button onclick="redirectToUserProfile(<%=listU.get(i).getId()%>)" class="view-btn">View</button></td>
                        </tbody>
            <%
                    }
                    
                    if (listU.size() < 5) {
                    %>
                        <style>
                        footer {
                            position: fixed;
                            bottom: 0;
                            left: 0;
                            width: 100%;
                            background-color: #333;
                            color: #fff;
                            text-align: center;
                            padding: 10px;
                        }
                    </style>
                    <%
                    }
                } else {
            %>
                    <h1>No Products Found!!!</h1>
                    <style>
                        footer {
                            position: fixed;
                            bottom: 0;
                            left: 0;
                            width: 100%;
                            background-color: #333;
                            color: #fff;
                            text-align: center;
                            padding: 10px;
                        }
                    </style>
            <%
                }
            %>
        </table>
    </div>

    <%@include file="footer.jsp" %>
    
    <script>
        function redirectToUserProfile(id) {
            window.location.href = 'update?action=user&id=' + id;
        }
        
        function redirectToAddUser() {
            window.location.href = 'add?action=user';
        }
    </script>
</body>
</html>
