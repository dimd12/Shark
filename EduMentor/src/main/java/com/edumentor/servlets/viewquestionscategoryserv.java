package com.edumentor.servlets;

import com.edumentor.models.Category;
import com.edumentor.models.Post;
import com.edumentor.models.Question;
import com.edumentor.models.User;
import com.edumentor.services.*;
import com.edumentor.services.impl.CategoryServiceImpl;
import com.edumentor.services.impl.PostServiceImpl;
import com.edumentor.services.impl.QuestionServiceImpl;
import com.edumentor.services.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet to display a specific post and its reviews in the CMS.
 * Retrieves post details and associated reviews to be displayed on the view post page.
 *
 * @author adrian
 */
@WebServlet(name = "viewquestionscategoryserv", urlPatterns = {"/viewquestionscategoryserv"})
public class viewquestionscategoryserv extends HttpServlet {

        QuestionServiceIntf questionService = QuestionServiceImpl.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, retrieves the post by its ID using {@link PostServiceIntf},
     * retrieves the reviews for the post using {@link ReviewServiceIntf}, sets the user, post, and review list as request attributes,
     * and forwards the request to the view post page.</p>
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

            // Get the category service implementation
            CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
            // Get the list of all categories
            List<Category> categoryList = categoryService.findAll();
            // Set the list of categories as an attribute of the request
            request.setAttribute("categoryList", categoryList);

            // Get the post ID from the request parameter
            int id = Integer.parseInt(request.getParameter("categoryId"));
            // Find the post by ID
            List<Question> questionList = questionService.findByCategoryId(id);
            // Set the post object as an attribute of the request
            request.setAttribute("questionList", questionList);

            response.sendRedirect("/cms/questions-category.html?categoryId=" + id);


            // Catch any exceptions that occur during the process
        } catch (Exception ex) {

            // Get the category service implementation
            CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
            // Get the list of all categories
            List<Category> categoryList = categoryService.findAll();
            // Set the list of categories as an attribute of the request
            request.setAttribute("categoryList", categoryList);

            // Get the post ID from the request parameter
            int id = Integer.parseInt(request.getParameter("categoryId"));
            // Find the post by ID
            List<Question> questionList = questionService.findByCategoryId(id);
            // Set the post object as an attribute of the request
            request.setAttribute("questionList", questionList);

            // Forward the request to the view post page
            String path = "/WEB-INF/pages/viewquestionscategory.jsp";
            request.getRequestDispatcher(path).forward(request, response);

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
        return "Servlet to display a specific post and its reviews in the CMS. Retrieves post details and associated reviews to be displayed on the view post page.";
    }// </editor-fold>

}
