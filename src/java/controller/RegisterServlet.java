/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.User;

/**
 *
 * @author Hieu
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
            response.sendRedirect(request.getContextPath());
        } else {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        User cU = (User) session.getAttribute("user");
        if (cU == null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String fullname = request.getParameter("fullname");

            String regex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,7}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                if (User.isStrongPassword(password)) {
                    if (User.isValidUsername(username)) {
                        boolean exist = new UserDAO().checkExistedUser(username, email);
                        if (exist == false) {
                            new UserDAO().register(username, password, email, fullname);
                            response.sendRedirect("login");
                        } else {
                            String err = "Tài khoản hoặc email đã tồn tại!";
                            request.setAttribute("err", err);
                            request.getRequestDispatcher("register.jsp").forward(request, response);
                        }
                    } else {
                        String err = "Tài khoản cần chữ và số!";
                        request.setAttribute("err", err);
                        request.getRequestDispatcher("register.jsp").forward(request, response);
                    }
                } else {
                    String err = "Mật khẩu không đủ mạnh!";
                    request.setAttribute("err", err);
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                }
            } else {
                String err = "Email không đúng định dạng!";
                request.setAttribute("err", err);
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }

        } else {
            if (cU.getRole().equals("admin")) {
                response.sendRedirect("admin");
            } else {
                response.sendRedirect(request.getContextPath());
            }
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
