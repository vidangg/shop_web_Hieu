<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.Category" %>
<%@ page import="model.Comment" %>
<%@ page import="model.User" %>
<%@ page import="dao.CategoryDAO" %>
<%@ page import="dao.UserDAO" %>
<%@ page import="dao.OrderItemDAO" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Chi tiết sản phẩm</title>
        <link type="text/css" rel="stylesheet" href="css/start.css">
        <link type="text/css" rel="stylesheet" href="css/product.css">
    </head>
    <body>
        <%@include file="header.jsp" %>
        
        <form id="form" name="f" action="" method="POST" class="product-container">
            <%
                Product p = (Product) request.getAttribute("product");
                if (p != null) {
                    Category c = new CategoryDAO().getCategoryById(p.getCategory_id());
            %>
                <img src="<%=p.getImage_url()%>" alt="<%=p.getName()%>" class="product-image">
                <div class="product-details">
                    <div class="product-details-section">
                        <div>
                            <input id="productId" name="productId" type="hidden" value="<%=p.getId()%>">
                            <h1><%=p.getName()%></h1>
                            <p class="price"><%=p.getPrice()%></p>
                            <p>Hãng: <%=c.getName()%></p>
                            <p>Mô tả: <%=p.getDescription()%></p>
                            <p>Số lượng trong kho: <%=p.getStock()%></p>
                            <c:if test="${not empty sessionScope.user && sessionScope.user.getRole() == 'admin'}">
                                <p>Ngày tạo: <%=p.getCreated()%></p>
                                <p>Ngày sửa: <%=p.getUpdated()%></p>
                            </c:if>

                        </div>
                        <c:if test="${empty sessionScope.user || sessionScope.user.getRole() == 'customer'}">
                            <div class="quantity-selector">
                                <button type="button" onclick="decrease()">-</button>
                                <input type="text" value="1" name="quantity" id="quantity">
                                <button type="button" onclick="increase()">+</button>
                            </div>
                        </c:if>
                    </div>
                    <div class="product-actions">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user && sessionScope.user.getRole() == 'admin'}">
                                <style>
                                    .hidden-now {
                                        background-color: #8b4513;
                                        color: white;
                                    }

                                    .hidden-now:hover {
                                        background-color: #8b3113;
                                    }
                                </style>
                                <button onclick="upd(event, <%=p.getId()%>)" class="buy-now">Sửa</button>
                                <%
                                    if (p.getType().equals("appear")) {
                                %>
                                        <button onclick="editType(event)" class="hidden-now">Ẩn</button>
                                <%
                                    } else {
                                %>
                                        <button onclick="editType(event)" class="hidden-now">Hiện</button>
                                <%
                                    }
                                %>
                                <button onclick="dele(event)" class="add-to-cart">Xóa</button>
                            </c:when>

                            <c:otherwise>
                                <button onclick="buy(event)" class="buy-now">Mua ngay</button>
                                <button onclick="cart(event)" class="add-to-cart">Thêm giỏ hàng</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            <%
                }
            %>
        </form>

        <div class="comments-container">
            <h2>Bình luận</h2>
            <form action="comment" method="POST" class="comment-form">
                <input id="action" name="action" type="hidden" value="create">
                <input id="productId" name="productId" type="hidden" value="<%=p.getId()%>">
                <textarea id="commentText" name="commentText" placeholder="Viết bình luận của bạn..."></textarea>
                <%
                    String err = (String) request.getAttribute("err");
                    if(err != null){
                %>
                        <div style="color: red; margin-bottom: 5px"><%= err %></div>
                <%   
                    }
                %> 
                <button id="postComment">Gửi bình luận</button>
            </form>
            <div id="commentsList">
                <%
                    List<Comment> listC = (List<Comment>) request.getAttribute("listC");
                    User u = (User) request.getAttribute("user");
                    if (listC != null) {
                        for (int i = 0; i < listC.size(); i++) {
                            User commentU = new UserDAO().getUserById(listC.get(i).getUser_id());
                %>
                            <div class="comment">
                                <div class="comment-header">
                                    <span class="comment-author"><%=commentU.getFullname()%></span>
                                    <span class="comment-time"><%=listC.get(i).getCreated()%></span>
                                </div>
                                <p><%=listC.get(i).getComment()%></p>
                                <%
                                    if (u != null && (u.getId() == listC.get(i).getUser_id() || u.getRole().equals("admin"))) {
                                %>
                                <form action="comment" method="POST">
                                    <input id="action" name="action" type="hidden" value="delete">
                                    <input id="productId" name="productId" type="hidden" value="<%=p.getId()%>">
                                    <input id="commentId" name="commentId" type="hidden" value="<%=listC.get(i).getId()%>">
                                    <button id="deleteComment" class="delete-button">Xóa</button>
                                </form>
                                <%
                                    }
                                %>
                            </div>
                <%
                        }
                    }
                %>
            </div>
        </div>
        
        <%@include file="footer.jsp" %>

        <script>
            function increase() {
                var quantity = document.getElementById("quantity");
                if (quantity.value < <%=p.getStock()%>) {
                    quantity.value++;
                }
            }

            function decrease() {
                var quantity = document.getElementById("quantity");
                if (quantity.value > 1) {
                    quantity.value--;
                }
            }
            
            function buy(event) {
                if (${not empty user}) {
                    var quantity = document.getElementById("quantity").value;
                    var money = ${user.getMoney()};
                    event.preventDefault();
                    if (money >= (quantity * <%=p.getNumberPrice()%>)) {
                        alert("Mua sản phầm thành công!");
                        document.f.action = "product?action=buy";
                        document.f.method = "POST";
                        document.f.submit();
                    } else {
                        alert("Bạn không đủ tiền trong tài khoản. Vui lòng liên hệ admin để nạp thêm tiền!");
                    }
                } else {
                    window.location.href = 'login';
                }
            }
            
            function cart(event) {
                if (${not empty user}) {
                    event.preventDefault();
                    alert("Thêm sản phầm thành công!");
                    document.f.action = "product?action=cart";
                    document.f.method = "POST";
                    document.f.submit();
                } else {
                    window.location.href = 'login';
                }
            }
        </script>
        <c:if test="${not empty sessionScope.user}">
            <c:set var="user" value="${sessionScope.user}" />
            <c:if test="${user.getRole() == 'admin'}">   
                <script>
                    function upd(event, id) {
                        if (${not empty user}) {
                            event.preventDefault();
                            window.location.href = "update?action=product&id=" + id;
                        } else {
                            window.location.href = 'login';
                        }
                    }
                    
                    function editType(event, id) {
                        if (${not empty user}) {
                            document.f.action = "update?action=editType";
                            document.f.submit();
                        } else {
                            window.location.href = 'login';
                        }
                    }
                    
                    function dele() {
                        var isExists = <%=new OrderItemDAO().isOrderItemByProduct(p.getId())%>
                        if (isExists) {
                            event.preventDefault();
                            alert("Sản phẩm đã có trong đơn hàng, không thể hủy. Bạn có thể ẩn sản phẩm đi!")
                        } else {
                            if (${not empty user}) {
                                alert("Xóa sản phẩm thành công!");
                                document.f.action = "delete?action=product";
                                document.f.submit();
                            } else {
                                window.location.href = 'login';
                            }
                        }
                    }
                </script>
            </c:if>
        </c:if>
    </body>
</html>
