<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>
<%@ page import="model.Product" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product Form</title>
    <link rel="stylesheet" href="css/addProduct.css">
    <link rel="stylesheet" href="css/start.css">
</head>
<body>
    <%@include file="header.jsp" %>
    
    <div class="ap-container">
        <h1>Add Product</h1>
        <form action="update?action=product" method="POST" id="add-product-form" enctype="multipart/form-data">
            <%
                Product p = (Product) request.getAttribute("product");
                if (p != null) {
            %>
                <div class="form-group">
                    <label for="name">Id:</label>
                    <input type="text" id="id" name="id" value="<%=p.getId()%>" readonly="readonly">
                </div>
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" value="<%=p.getName()%>" required>
                </div>
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" rows="4"><%=p.getDescription()%></textarea>
                </div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" id="price" name="price" value="<%=p.getBigPrice()%>" step="0.01" required>
                </div>
                <div class="form-group">
                    <label for="stock">Stock:</label>
                    <input type="number" id="stock" name="stock" value="<%=p.getStock()%>" required>
                </div>
                <div class="form-group">
                    <label for="image">Image Link:</label>
                    <input type="file" id="image" name="image" required>
                    <p id="image-url"></p>
                </div>
                <div class="form-group">
                    <label for="category">Category:</label>
                    <select id="category" name="category">
                    <%
                        List<Category> listC = (List<Category>) request.getAttribute("listC");
                        if (listC != null) {
                            for (int i = 0; i < listC.size(); i++) {
                                if(p.getCategory_id() == listC.get(i).getId()) {
                    %>  
                                    <option value="<%=listC.get(i).getId()%>" selected="selected"><%=listC.get(i).getName()%></option>
                    <%          } else {
                    %>
                                    <option value="<%=listC.get(i).getId()%>"><%=listC.get(i).getName()%></option>
                    <%
                                }
                            }
                        }
                    %>
                    </select>
                </div>
                    
                <div class="form-group">
                    <label for="type">Type:</label>
                    <select id="type" name="type">
                    <%
                        if (p.getType().equals("appear")){
                    %>  
                            <option value="appear" selected="selected">Hiện</option>
                            <option value="hidden">Ẩn</option>
                    <%  
                        } else {
                    %>
                            <option value="appear">Hiện</option>
                            <option value="hidden" selected="selected">Ẩn</option>
                    <%
                        }
                    %>
                    </select>
                </div>

                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                        <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %> 
                <button type="submit" class="add-button">Update Product</button>
            <%
                }
            %>
        </form>
    </div>
    
    <%@include file="footer.jsp" %>
</body>
</html>
