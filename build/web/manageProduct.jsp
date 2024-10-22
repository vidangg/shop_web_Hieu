<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
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
        <h1>Quản lý sản phẩm</h1>
        <button onclick="redirectToAddProduct()" id="add-product-btn">Add Product</button>
        <table id="product-table">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Created</th>
                    <th>Updated</th>
                    <th>View</th>
                </tr>
            </thead>
            <%
                List<Product> listP = (List<Product>) request.getAttribute("listP");
                if (listP != null) {
                    for (int i = 0; i < listP.size(); i++) {  
            %>
                        <tbody>
                            <td><%=listP.get(i).getId()%></td>
                            <td><img src="<%=listP.get(i).getImage_url()%>" alt="<%=listP.get(i).getName()%>"></td>
                            <td><%=listP.get(i).getName()%></td>
                            <td><%=listP.get(i).getCreated()%></td>
                            <td><%=listP.get(i).getUpdated()%></td>
                            <td><button onclick="redirectToProduct(<%=listP.get(i).getId()%>)" class="view-btn">View</button></td>
                        </tbody>
            <%
                    }
                    if (listP.size() < 2) {
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
        function redirectToProduct(id) {
            window.location.href = 'product?id=' + id;
        }
        
        function redirectToAddProduct() {
            window.location.href = 'add?action=product';
        }
    </script>
</body>
</html>
