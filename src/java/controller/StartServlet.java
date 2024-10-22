/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.CategoryDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;
import model.Product;

/**
 *
 * @author Hieu
 */
@WebServlet(name = "StartServlet", urlPatterns = {"/start"})
public class StartServlet extends HttpServlet {

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
            out.println("<title>Servlet StartServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StartServlet at " + request.getContextPath() + "</h1>");
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
        int pageId = 1;
        int cateId = 0;
        String search = "";
        if (request.getParameter("pageId") != null) {
            pageId = Integer.parseInt(request.getParameter("pageId"));
        }
        if (request.getParameter("cateId") != null) {
            cateId = Integer.parseInt(request.getParameter("cateId"));
        }
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }
        List<Product> listP = null;
        
        if (search.equals("")) {
            if(cateId == 0) {
                listP = new ProductDAO().getProduct();
            } else {
                listP = new ProductDAO().getProductByCate(cateId);
            }
        } else {
            if(cateId == 0) {
                listP = new ProductDAO().getProductByName(search);
            } else {
                listP = new ProductDAO().getProductByNameAndCate(search, cateId);
            }
        }
        
        List<Category> listC = new CategoryDAO().getAll();
        
        if(listP.size() > 8) {
            List<Product> showList = listP.subList((pageId - 1) * 8, Math.min(pageId * 8, listP.size()));
            request.setAttribute("listP", showList);
            request.setAttribute("pageId", pageId);
            
        } else {
            request.setAttribute("listP", listP);
            request.setAttribute("pageId", 1);
        }
        
        request.setAttribute("cateId", cateId);
        request.setAttribute("search", search);
        request.setAttribute("maxPage", (int) (listP.size() / 8));
        request.setAttribute("listC", listC);
        request.getRequestDispatcher("start.jsp").forward(request, response);
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
        processRequest(request, response);
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
