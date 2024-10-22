<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="model.Product" %>
<%@ page import="model.Order" %>
<%@ page import="model.Order_item" %>
<%@ page import="dao.OrderItemDAO" %>
<%@ page import="dao.ProductDAO" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đơn hàng</title>
    <link type="text/css" rel="stylesheet" href="css/start.css">
    <link type="text/css" rel="stylesheet" href="css/order.css">
    <style>
        .cancel-button {
            background-color: #8b4513;
            color: white;
        }

        .cancel-button:hover {
            background-color: #8b2513;
        }
    </style>
</head>
<body>
    <%@include file="header.jsp" %>
    
    <div class="order-history-container">
        <h1>Lịch sử mua hàng của bạn</h1>
        <%
            List<Order> listO = (List<Order>) request.getAttribute("listO");
            if (listO != null) {
                if (listO.size() < 2) {
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
                for (int i = 0; i < listO.size(); i++) {
        %>
                    <div class="order-item">
                        <h2>Mã đơn hàng: <%=listO.get(i).getId()%></h2>
                        <p>Ngày đặt hàng: <%=listO.get(i).getCreated()%></p>
                        <p>Trạng thái: <span class="order-status pending"><%=listO.get(i).getStatus()%></span></p>
                        <div class="order-details">
                            <%
                                List<Order_item> listOI = (List<Order_item>) new OrderItemDAO().getOrderItemByOrderId(listO.get(i).getId());
                                if (listOI != null) {
                                    for (int j = 0; j < listOI.size(); j++) {
                                        Product p = new ProductDAO().getProductById(listOI.get(j).getProduct_id());
                                        if (p != null) {
                            %>
                                            <div class="product-item">
                                                <img src="<%=p.getImage_url()%>" alt="<%=p.getName()%>" class="product-image">
                                                <div class="product-info">
                                                    <h3><%=p.getName()%></h3>
                                                    <p>Số lượng: <%=listOI.get(j).getQuantity()%></p>
                                                    <p>Giá: <%=listOI.get(j).getPrice()%></p>
                                                </div>
                                            </div>
                            <%
                                        }
                                    }
                                }
                            %>
                        </div>
                        <div class="order-total">
                            <%
                                Locale localeVN = new Locale("vi", "VN");
                                NumberFormat vnFormat = NumberFormat.getCurrencyInstance(localeVN);
                                String total = vnFormat.format(listO.get(i).getTotal());
                            %>
                            <p>Tổng cộng: <span class="total-price"><%=total%></span></p>
                        </div>
                        <form id="form" name="f" action="" method="POST" class="order-actions">
                            <%
                                if (listO.get(i).getStatus().equals("pending")) {
                            %>
                                    <button onclick="transport(event, <%=listO.get(i).getId()%>)" class="confirm-button">Giao Hàng</button>
                                    <button onclick="canc(event, <%=listO.get(i).getId()%>)" class="cancel-button">Hủy</button>
                            <%
                                }
                            %>
                                <button onclick="dele(event, <%=listO.get(i).getId()%>)" class="remove-button">Xóa</button>
                        </form>
                        
                    </div>
        <%
                }
            } else {
        %>
                <h1>No Order Found!!!</h1>
        <%
            }
        %>
        
        <script>
            function transport(event, orderId) {
                event.preventDefault();
                const form = document.getElementById("form");
                if (${not empty user}) {
                    alert("Đơn hàng đang vận chuyển!");
                    form.action = "update?action=tranOrder&orderId=" + orderId;
                    form.submit();
                } else {
                    window.location.href = 'login';
                }
            }
            
            function canc(event, orderId) {
                event.preventDefault();
                const form = document.getElementById("form");
                if (${not empty user}) {
                    alert("Hủy đơn hàng thành công!");
                    form.action = "update?action=canOrder&orderId=" + orderId;
                    form.submit();
                } else {
                    window.location.href = 'login';
                }
            }
            
            function dele(event, orderId) {
                event.preventDefault();
                const form = document.getElementById("form");
                if (${not empty user}) {
                    alert("Xóa đơn hàng thành công!");
                    form.action = "delete?action=order&orderId=" + orderId;
                    form.submit();
                } else {
                    window.location.href = 'login';
                }
            }
        </script>
    </div>
    
    <%@include file="footer.jsp" %>
</body>
</html>
