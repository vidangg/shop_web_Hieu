<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Header</title>
    </head>
    <body>
        <header>
            <div class="logo">
                <c:choose>
                    <c:when test="${not empty sessionScope.user && sessionScope.user.getRole() == 'admin'}">
                        <h1><a href="/MobileShop/admin">Mobile Shop</a></h1>
                    </c:when>

                    <c:otherwise>
                        <h1><a href="/MobileShop/start">Mobile Shop</a></h1>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="header-right">
                <%
                    String key = (String) request.getAttribute("search");
                    if (key == null) key = "";
                %>
                <c:if test="${not empty sessionScope.user}">
                    <c:set var="user" value="${sessionScope.user}" />
                    <c:if test="${user.getRole() == 'admin'}">
                        <button onclick="redirectToManageUser()" class="cart">Người dùng</button>
                        <button onclick="redirectToManageProduct()" class="cart">Sản phẩm</button>
                        <button onclick="redirectToManageOrder()" class="cart">Đơn hàng</button>
                        <button onclick="redirectToProfilePage()">My Profile</button>
                        <button onclick="redirectToLogOutPage()">Log Out</button>
                    </c:if>
                    
                    <c:if test="${user.getRole() == 'customer'}">
                        <input type="text" placeholder="Tìm kiếm sản phẩm..." id="keyword" onkeypress="searchProduct(event)">
                        <input type="hidden" id="searchKeyword" value="<%=key%>">
                        <button onclick="redirectToCartPage()" class="cart"><img src="img/cart-ic.png" alt="Giỏ hàng">Giỏ hàng</button>
                        <button onclick="redirectToOrderPage()" class="cart"><img src="img/order-ic.png" alt="Đơn của tôi">Đơn của tôi</button>
                        <button onclick="redirectToProfilePage()">My Profile</button>
                        <button onclick="redirectToLogOutPage()">Log Out</button>
                    </c:if>
                </c:if>

                <c:if test="${empty sessionScope.user}">
                    <input type="text" placeholder="Tìm kiếm sản phẩm..." id="keyword" onkeypress="searchProduct(event)">
                    <input type="hidden" id="searchKeyword" value="<%=key%>">
                    <button onclick="redirectToLoginPage()" class="cart"><img src="img/cart-ic.png" alt="Giỏ hàng">Giỏ hàng</button>
                    <button onclick="redirectToLoginPage()" class="cart"><img src="img/order-ic.png" alt="Đơn của tôi">Đơn của tôi</button>
                    <button onclick="redirectToLoginPage()">Log In</button>
                    <button onclick="redirectToRegisterPage()">Register</button>
                </c:if>
            </div>
        </header>

        <c:if test="${not empty sessionScope.user}">
            <c:set var="user" value="${sessionScope.user}" />
            <c:if test="${user.getRole() == 'admin'}">
                <script>
                    function redirectToManageUser() {
                        window.location.href = 'manageUser';
                    }
                    
                    function redirectToManageProduct() {
                        window.location.href = 'manageProduct';
                    }
                    
                    function redirectToManageOrder() {
                        window.location.href = 'manageOrder';
                    }
                </script>
            </c:if>
        </c:if>
        <script>
            function searchProduct(event) {
                if (event.keyCode === 13) {
                    var search = document.getElementById('keyword').value;
                    window.location.href = 'start?search=' + search;
                }
            }
            
            function redirectToLoginPage() {
                window.location.href = 'login';
            }

            function redirectToRegisterPage() {
                window.location.href = 'register';
            }

            function redirectToLogOutPage() {
                window.location.href = 'logout';
            }

            function redirectToProfilePage() {
                window.location.href = 'profile';
            }
            
            function redirectToOrderPage() {
                window.location.href = 'order';
            }
            
            function redirectToCartPage() {
                window.location.href = 'cart';
            }
        </script>
    </body>
</html>