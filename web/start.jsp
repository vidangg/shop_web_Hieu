<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Product" %>
<%@ page import="model.Category" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Mobile Shop</title>
        <link type="text/css" rel="stylesheet" href="css/start.css">
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}" />
        <%@include file="header.jsp" %>

        <div class="container">
            <div class="category">
                <%
                    Integer maxPage = (Integer) request.getAttribute("maxPage");
                    Integer cateId = (Integer) request.getAttribute("cateId");
                %>
                <select id="categorySelect" onchange="getSelectedCategory(<%= maxPage %>)">
                    <option value="0" 
                            <%
                                if (cateId == 0) {
                            %>
                                    selected="selected"
                            <%
                                }
                            %>>Tất cả danh mục</option>
                    <%
                        List<Category> listC = (List<Category>) request.getAttribute("listC");
                        if (listC != null) {
                            for (int i = 0; i < listC.size(); i++) {
                                if(listC.get(i).getId() == cateId) {
                    %>
                                    <option value="<%=listC.get(i).getId()%>" selected="selected"><%=listC.get(i).getName()%></option>
                    <%
                                } else {
                    %>
                                    <option value="<%=listC.get(i).getId()%>"><%=listC.get(i).getName()%></option>
                    <%
                                }
                            }
                        }
                    %>
                </select>
            </div>
            <div class="product-list">
                <%
                    List<Product> listP = (List<Product>) request.getAttribute("listP");
                    if (listP != null && listP.size() > 0) {
                        for (int i = 0; i < listP.size(); i++) {                    
                %>
                            <div class="product">
                                <img src="<%=listP.get(i).getImage_url()%>" alt="<%=listP.get(i).getName()%>">
                                <h2><%=listP.get(i).getName()%></h2>
                                <p class="price"><%=listP.get(i).getPrice()%></p>
                                <p>Tặng: Miễn phí BHV lần thứ 5, khi đã mua BHV lần thứ 4.</p>
                                
                                <a href="product?id=<%=listP.get(i).getId()%>" class="buy-btn">MUA</a>
                            </div>
                <%
                        }
                        
                        if (listP.size() < 5) {
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

                                .pagination {
                                    text-align: center;
                                    margin-top: 40px;
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
                            
                            .pagination {
                                text-align: center;
                                margin-top: 320px;
                            }
                        </style>
                <%
                    }
                %>
            </div>
        </div>
        <div class="pagination">
            <%
                Integer pageId = (Integer) request.getAttribute("pageId");
                if (pageId == null) {
                    pageId = 1;
                }
            %>
            <button onclick="nextPage(<%= pageId - 1%>, <%= maxPage %>)">&laquo;</button>
            <input type="text" value="<%= pageId %>" name="pageId" id="pageId" onkeypress="handleKeyPress(event, <%= maxPage %>)">
            <button onclick="nextPage(<%= pageId + 1%>, <%= maxPage %>)">&raquo;</button>
        </div>

        <%@include file="footer.jsp" %>
    </body>

    <script>
        function handleKeyPress(event, maxPage) {
            if (event.keyCode === 13) {
                var pageId = document.getElementById('pageId').value;
                nextPage(pageId, maxPage);
            }
        }
        
        function nextPage(page, maxPage) {
            if (page <= 0){
                page = 1;
            }
            if (page > (maxPage + 1)) {
                page = maxPage + 1;
            }
            var cate = document.getElementById('categorySelect').value;
            var search = document.getElementById('searchKeyword').value;
            window.location.href = 'start?pageId=' + page + '&cateId=' + cate + '&search=' + search;
        }
        
        function getSelectedCategory(maxPage) {
            nextPage(1, maxPage);
        }
        
        function searchProduct(event) {
            if (event.keyCode === 13) {
                var cate = document.getElementById('categorySelect').value;
                var search = document.getElementById('keyword').value;
                window.location.href = 'start?pageId=' + 1 + '&cateId=' + cate + '&search=' + search;
            }
        }
    </script>
</html>