/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CommentDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Comment;
import model.Product;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="CommentServlet", urlPatterns={"/comment"})
public class CommentServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CommentServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CommentServlet at " + request.getContextPath () + "</h1>");
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
        response.sendRedirect("start");
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
            if (action.equals("create")) {
                int productId = Integer.parseInt(request.getParameter("productId"));
                String commentText = request.getParameter("commentText").trim();
                if (commentText != null) {
                    String commentFinal = commentText.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                    new CommentDAO().newComment(u.getId(), productId, commentFinal);
                    response.sendRedirect("product?id=" + productId);
                } else {
                    Product p = new ProductDAO().getProductById(productId);
                    List<Comment> listC = new CommentDAO().getCommentByProduct(productId);

                    String err = "The comment is empty! Try again!";

                    request.setAttribute("product", p);
                    request.setAttribute("listC", listC);
                    request.setAttribute("user", u);
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("product.jsp").forward(request, response);
                }
            } else if (action.equals("delete")) {
                int commentId = Integer.parseInt(request.getParameter("commentId"));
                int productId = Integer.parseInt(request.getParameter("productId"));
                new CommentDAO().deleteComment(commentId);
                response.sendRedirect("product?id=" + productId);
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
