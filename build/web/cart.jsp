<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.Product" %>
<%@ page import="model.Cart" %>
<%@ page import="dao.ProductDAO" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ Hàng</title>
    <link type="text/css" rel="stylesheet" href="css/start.css">
    <link type="text/css" rel="stylesheet" href="css/cart.css">
</head>
<body>
    <%@include file="header.jsp" %>
    
    <div class="cart-container">
        <h1>Giỏ hàng của bạn</h1>
        <%
            List<Cart> listC = (List<Cart>) request.getAttribute("listC");
            if (listC.size() < 3) {
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
            double sum = 0;
            for (int i = 0; i < listC.size(); i++) {
                Product p = new ProductDAO().getProductById(listC.get(i).getProduct_id());
                if (p != null) {
                    sum += p.getNumberPrice() * listC.get(i).getQuantity();
        %>
                <div class="cart-item">
                    <img src="<%=p.getImage_url()%>" alt="<%=p.getName()%>" class="cart-item-image">
                    <div class="cart-item-details">
                        <h2><%=p.getName()%></h2>
                        <p class="cart-item-price"><%=p.getPrice()%></p>
                        <div class="quantity-selector">
                            <form action="cart" method="POST">
                                <input id="action" name="action" type="hidden" value="decrease">
                                <input id="cartId" name="cartId" type="hidden" value="<%=listC.get(i).getId()%>">
                                <input id="quantity" name="quantity" type="hidden" value="<%=listC.get(i).getQuantity()%>">
                                <button id="decrease">-</button>
                            </form>
                            <input type="text" id="quantity" name="quantity" value="<%=listC.get(i).getQuantity()%>" readonly>
                            <form action="cart" method="POST">
                                <input id="action" name="action" type="hidden" value="increase">
                                <input id="cartId" name="cartId" type="hidden" value="<%=listC.get(i).getId()%>">
                                <input id="quantity" name="quantity" type="hidden" value="<%=listC.get(i).getQuantity()%>">
                                <input id="maxQ" name="maxQ" type="hidden" value="<%=p.getStock()%>">
                                <button id="increase">+</button>
                            </form>
                        </div>
                    </div>
                    <form action="cart" method="POST">
                        <input id="action" name="action" type="hidden" value="delete">
                        <input id="cartId" name="cartId" type="hidden" value="<%=listC.get(i).getId()%>">
                        <button class="remove-button">Xóa</button>
                    </form>
                </div>
        <%
                }
            }
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
            String total = vnFormat.format(sum);
        %>
        
        <div class="cart-total">
            <p>Tổng cộng: <span class="total-price"><%=total%></span></p>
            <form name="form" action="cart" method="POST">
                <input id="action" name="action" type="hidden" value="buy">
                <input id="sum" name="sum" type="hidden" value="<%=sum%>">
                <button onclick="checksum(event)" class="checkout-button">Mua</button>
            </form>
        </div>
    </div>
                
    <script>
        function checksum(event) {
            event.preventDefault();
            var money = ${user.getMoney()};
            if (<%=sum%> > money) {
                alert("Tài khoản không đủ tiền. Vui lòng liên hệ admin!");
            } else {
                document.form.submit();
            }
        }
    </script>
    
    <%@include file="footer.jsp" %>
</body>
</html>
