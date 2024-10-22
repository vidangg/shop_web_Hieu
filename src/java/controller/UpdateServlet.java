/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CategoryDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.ProductDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import model.Category;
import model.Order;
import model.Order_item;
import model.Product;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="UpdateServlet", urlPatterns={"/update"})
@MultipartConfig
public class UpdateServlet extends HttpServlet {
   
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
            out.println("<title>Servlet UpdateServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateServlet at " + request.getContextPath () + "</h1>");
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
        User u = (User) session.getAttribute("user");
        if (u != null) {
            if (u.getRole().equals("admin")) {
                String action = request.getParameter("action");
                if (action.equals("product")) {
                    int productId = Integer.parseInt(request.getParameter("id"));
                    Product p = new ProductDAO().getProductById(productId);
                    
                    List<Category> listC = new CategoryDAO().getAll();
                    request.setAttribute("listC", listC);
                    request.setAttribute("product", p);
                    
                    request.getRequestDispatcher("updateProduct.jsp").forward(request, response);
                } else if (action.equals("user")) {
                    int id = Integer.parseInt(request.getParameter("id"));
                    User user = new UserDAO().getUserById(id);
                    request.setAttribute("user", user);
                    request.getRequestDispatcher("updateUser.jsp").forward(request, response);
                } else {
                    response.sendRedirect("start");
                }
            } else {
                response.sendRedirect("start");
            }
        } else {
            response.sendRedirect("login");
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
        if (u != null && u.getRole().equals("admin")) {
            String action = request.getParameter("action");
            if (action.equals("product")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String name = request.getParameter("name");
                String description = request.getParameter("description");
                Double price = Double.valueOf(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                int category = Integer.parseInt(request.getParameter("category"));
                String type = request.getParameter("type");
                
                Part part = request.getPart("image");
                String fileName = Paths.get(part.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + "img" + File.separator + fileName;

                // Create upload directory if it doesn't exist
                File uploadDir = new File(getServletContext().getRealPath("") + "img");
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                
                // Ensure the file name is unique by appending a timestamp if the file already exists
                if (!Files.exists(Paths.get(uploadPath))) {
//                    fileName = System.currentTimeMillis() + "_" + fileName;
//                    uploadPath = getServletContext().getRealPath("") + "img" + File.separator + fileName;
                    try (InputStream inputStream = part.getInputStream()) {
                        Files.copy(inputStream, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
                    }
                }

                new ProductDAO().updateProduct(id, name, description, price, stock, "img/" + fileName, category, type);
                response.sendRedirect("manageProduct");
            } else if(action.equals("editType")){
                int id = Integer.parseInt(request.getParameter("productId"));
                Product p = new ProductDAO().getProductById(id);
                if (p.getType().equals("appear")) {
                    new ProductDAO().updateType(id, "hidden");
                } else {
                    new ProductDAO().updateType(id, "appear");
                }
                response.sendRedirect("product?id=" + id);
            } else if (action.equals("user")) {
                int id = Integer.parseInt(request.getParameter("id"));
                String username = request.getParameter("username");
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                BigDecimal money = new BigDecimal(request.getParameter("money"));
                String role = request.getParameter("role");
                String password = request.getParameter("password");
                
                User us = new User(id, username, email, password, fullname, money, role);
                new UserDAO().updateUser(us);
                response.sendRedirect("manageUser");
            } else if (action.equals("tranOrder")) {
                int id = Integer.parseInt(request.getParameter("orderId"));
                new OrderDAO().updateOrder(id, "transporting");
                response.sendRedirect("manageOrder");
            } else if (action.equals("canOrder")){
                int id = Integer.parseInt(request.getParameter("orderId"));
                new OrderDAO().updateOrder(id, "cancelled");
                List<Order_item> listOI = new OrderItemDAO().getOrderItemByOrderId(id);
                for (Order_item order_item : listOI) {
                    new ProductDAO().updateStock(order_item.getProduct_id(), order_item.getQuantity());
                }
                Order o = new OrderDAO().getOrderById(id);
                new UserDAO().updateUserMoney(o.getUser_id(), new BigDecimal(-o.getTotal()));
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
