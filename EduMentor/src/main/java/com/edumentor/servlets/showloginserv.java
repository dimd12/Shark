package com.edumentor.servlets;

import com.edumentor.models.Category;
import com.edumentor.models.User;
import com.edumentor.services.CategoryServiceIntf;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.CategoryServiceImpl;
import com.edumentor.services.impl.UserServiceImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to display the login page.
 * If a user is already logged in, redirects them to the CMS index page. Otherwise, displays the login page with categories.
 *
 * @author adrian
 */
@WebServlet(name = "showloginserv", urlPatterns = {"/showloginserv"})
public class showloginserv extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method checks for a valid user session. If a user is logged in, it redirects them to the CMS index page.
     * Otherwise, it retrieves the list of all categories using {@link CategoryServiceIntf}, sets the category list as a request attribute,
     * and forwards the request to the login page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Get the current session
            HttpSession session = request.getSession();
            // If the session is null, throw an exception
            if (session == null) {
                throw new Exception("Session not found");
            }

            // Get the username of the current user from the session
            String currentUser = (String) session.getAttribute("CURRENTUSER");

            // If the current user is null, throw an exception
            if (currentUser == null) {
                throw new Exception("The user is null");
            }

            // Get the user service implementation
            UserServiceIntf userService = UserServiceImpl.getInstance();
            // Find the user object by username
            User user = userService.findByUsername(currentUser);

            // If the user is null, throw an exception
            if (user == null) {
                throw new Exception("The user is null");
            }

            // Set the user object as an attribute of the request
            request.setAttribute("user", user);

            // Redirect to the CMS index page
            response.sendRedirect("cms/index.html");

            // Catch any exceptions that occur during the process
        } catch (Exception ex) {

            // Get the category service implementation
            CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
            // Get the list of all categories
            List<Category> categoryList = categoryService.findAll();
            // Set the list of categories as an attribute of the request
            request.setAttribute("categoryList", categoryList);

            // Forward the request to the login page
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
            System.out.println("Error: " + ex.getMessage());
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
        processRequest(request, response);
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
        return "Servlet to display the login page. If a user is already logged in, redirects them to the CMS index page. Otherwise, displays the login page with categories.";
    }// </editor-fold>

}
