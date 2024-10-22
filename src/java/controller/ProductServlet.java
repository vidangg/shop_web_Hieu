/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CartDAO;
import dao.CommentDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import model.Cart;
import model.Comment;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="ProductServlet", urlPatterns={"/product"})
public class ProductServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User cU = (User) session.getAttribute("user");
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = new ProductDAO().getProductById(id);
        List<Comment> listC = new CommentDAO().getCommentByProduct(id);
        if (p != null) {
            request.setAttribute("product", p);
            request.setAttribute("listC", listC);
            request.setAttribute("user", cU);
            request.getRequestDispatcher("product.jsp").forward(request, response);
        } else {
            response.sendRedirect("start");
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User u = (User) session.getAttribute("user");
        if (u != null) {
            String action = request.getParameter("action");
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int productId = Integer.parseInt(request.getParameter("productId"));
            if(action.equals("cart")) {
                Cart c = new CartDAO().getCartByUserAndProduct(u.getId(), productId);
                if (c != null) {
                    new CartDAO().updateCart(c.getId(), c.getQuantity() + quantity);
                } else {
                    new CartDAO().newCart(u.getId(), productId, quantity);
                }
                response.sendRedirect("cart");
            } else if (action.equals("buy")) {
                Product p = new ProductDAO().getProductById(productId);
                new OrderDAO().newOrder(u.getId(), new BigDecimal(p.getNumberPrice()* quantity)
                        .setScale(2, BigDecimal.ROUND_HALF_UP));
                Order o = new OrderDAO().getNewestUserOrder(u.getId());
                new OrderItemDAO().newOrderItem(o.getId(), productId, quantity, p.getNumberPrice());
                
                new ProductDAO().updateStock(productId, -1 * quantity);
                
                new UserDAO().updateUserMoney(u.getId(), new BigDecimal(p.getNumberPrice()* quantity));
                u = new UserDAO().getUserById(u.getId());
                session.setAttribute("user", u);
                
                response.sendRedirect("order");
            }
        } else {
            response.sendRedirect("login");
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
