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
import java.util.List;
import model.Order;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="DeleteServlet", urlPatterns={"/delete"})
public class DeleteServlet extends HttpServlet {
   
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
            out.println("<title>Servlet DeleteServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet DeleteServlet at " + request.getContextPath () + "</h1>");
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
        processRequest(request, response);
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
        if (u != null && u.getRole().equals("admin")) {
            String action = request.getParameter("action");
            if (action.equals("product")) {
                int id = Integer.parseInt(request.getParameter("productId"));
                
                new CommentDAO().deleteCommentByProductId(id);
                new ProductDAO().deleteProduct(id);
                response.sendRedirect("manageProduct");
            } else if (action.equals("user")) {
                int id = Integer.parseInt(request.getParameter("id"));
                
                new CartDAO().deleteAllUserCart(id);
                List<Order> listO = new OrderDAO().getOrderByUserId(id);
                for (Order order : listO) {
                    new OrderItemDAO().deleteOrderItemByOrderId(order.getId());
                    new OrderDAO().deleteOrder(order.getId());
                }
                new CommentDAO().deleteCommentByUserId(id);
                new UserDAO().deleteUser(id);
                response.sendRedirect("manageUser");
            } else if (action.equals("order")) {
                int id = Integer.parseInt(request.getParameter("orderId"));
                
                new OrderItemDAO().deleteOrderItemByOrderId(id);
                new OrderDAO().deleteOrder(id);
                response.sendRedirect("manageOrder");
            } else {
                response.sendRedirect("start");
            }
            
        } else {
            response.sendRedirect("start");
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
