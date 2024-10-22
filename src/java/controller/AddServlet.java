/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import dao.UserDAO;
import java.io.PrintWriter;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Category;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name="AddProductServlet", urlPatterns={"/add"})
@MultipartConfig
public class AddServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String path)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AddProductServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddProductServlet at " + path + "</h1>");
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
                    List<Category> listC = new CategoryDAO().getAll();
                    request.setAttribute("listC", listC);
                    request.getRequestDispatcher("addProduct.jsp").forward(request, response);
                } else if (action.equals("user")) {
                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
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
                if (Files.exists(Paths.get(uploadPath))) {
//                    fileName = System.currentTimeMillis() + "_" + fileName;
//                    uploadPath = getServletContext().getRealPath("") + "img" + File.separator + fileName;
                    try (InputStream inputStream = part.getInputStream()) {
                        Files.copy(inputStream, Paths.get(uploadPath), StandardCopyOption.REPLACE_EXISTING);
                    }
                }                

                new ProductDAO().newProduct(name, description, price, stock, "img/" + fileName, category, type);
                response.sendRedirect("manageProduct");
            } else if (action.equals("user")) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                String fullname = request.getParameter("fullname");
                String email = request.getParameter("email");
                BigDecimal money = new BigDecimal(request.getParameter("money"));
                String role = request.getParameter("role");
                
                String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,7}$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(email);
                if (matcher.matches()) {
                    if (money.compareTo(new BigDecimal(0)) >= 0) {
                        if (User.isStrongPassword(password)) {
                            boolean exist = new UserDAO().checkExistedUser(username, email);
                            if (exist == false) {
                                new UserDAO().newUser(username, password, email, fullname, money, role);
                                response.sendRedirect("manageUser");
                            } else {
                                String err = "Tài khoản hoặc email đã tồn tại!";
                                request.setAttribute("err", err);
                                request.getRequestDispatcher("addUser.jsp").forward(request, response);
                            }
                        } else {
                            String err = "Mật khẩu không đủ mạnh!";
                            request.setAttribute("err", err);
                            request.getRequestDispatcher("addUser.jsp").forward(request, response);
                        }
                    } else {
                        String err = "Số tiền phải lớn hơn hoặc bằng 0!";
                        request.setAttribute("err", err);
                        request.getRequestDispatcher("addUser.jsp").forward(request, response);
                    }
                } else {
                    String err = "Email không đúng định dạng!";
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("addUser.jsp").forward(request, response);
                }
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
