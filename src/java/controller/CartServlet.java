/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CartDAO;
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
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="CartServlet", urlPatterns={"/cart"})
public class CartServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CartServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CartServlet at " + request.getContextPath () + "</h1>");
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
        if (cU == null) {
            response.sendRedirect("login");
        } else {
            List<Cart> listC = new CartDAO().getCartByUserId(cU.getId());
            request.setAttribute("listC", listC);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
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
            if (action.equals("decrease")) {
                int cartId = Integer.parseInt(request.getParameter("cartId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                if (quantity > 1) {
                    new CartDAO().updateCart(cartId, quantity - 1);
                } else {
                    new CartDAO().deleteCart(cartId);
                }
                response.sendRedirect("cart");
            } else if (action.equals("increase")) {
                int cartId = Integer.parseInt(request.getParameter("cartId"));
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                int maxQ = Integer.parseInt(request.getParameter("maxQ"));
                if (quantity < maxQ)
                    new CartDAO().updateCart(cartId, quantity + 1);
                response.sendRedirect("cart");
            } else if (action.equals("delete")) {
                int cartId = Integer.parseInt(request.getParameter("cartId"));
                new CartDAO().deleteCart(cartId);
                response.sendRedirect("cart");
            } else if (action.equals("buy")) {
                List<Cart> listC = new CartDAO().getCartByUserId(u.getId());
                if (listC.size() > 1) {
                    Double sum = Double.valueOf(request.getParameter("sum"));
                    BigDecimal b = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP);

                    new OrderDAO().newOrder(u.getId(), b);
                    Order o = new OrderDAO().getNewestUserOrder(u.getId());
                    for (int i = 0; i < listC.size(); i++) {
                        Product p = new ProductDAO().getProductById(listC.get(i).getProduct_id());
                        new OrderItemDAO().newOrderItem(o.getId(), listC.get(i).getProduct_id()
                                , listC.get(i).getQuantity(), p.getNumberPrice());
                        new CartDAO().deleteAllUserCart(u.getId());
                    }
                    
                    new UserDAO().updateUserMoney(u.getId(), new BigDecimal(sum));
                    u = new UserDAO().getUserById(u.getId());
                    session.setAttribute("user", u);
                    
                    response.sendRedirect("order");
                } else {
                    response.sendRedirect("cart");
                }
            } else {
                response.sendRedirect("start");
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
