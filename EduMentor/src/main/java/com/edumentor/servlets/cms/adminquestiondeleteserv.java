package com.edumentor.servlets.cms;

import com.edumentor.models.Question;
import com.edumentor.models.User;
import com.edumentor.services.QuestionServiceIntf;
import com.edumentor.services.impl.QuestionServiceImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to handle deletion of questions from the admin panel.
 * It checks the user's role and only allows admins or the question's owner to delete it.
 *
 * @author adrian
 */
@WebServlet(name = "adminquestiondeleteserv", urlPatterns = {"/admin/adminquestiondeleteserv"})
public class adminquestiondeleteserv extends HttpServlet {

    QuestionServiceIntf questionService = QuestionServiceImpl.getInstance();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves the question ID from the request, verifies the user's session and role,
     * and then deletes the question if the user has the appropriate permissions (admin or the question's owner).
     * After deletion, it forwards the request to the admin questions page.</p>
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

            // Get the current user from the session
            User currentUser = (User) session.getAttribute("CURRENTUSER");
            // If the current user is null, throw an exception
            if (currentUser == null) {
                throw new Exception("The user is null");
            }

            // If the user's role ID is null or the role name is null, throw an exception
            if (currentUser.getRoleId() == null || currentUser.getRoleId().getRoleName() == null) {
                throw new Exception("User role is not defined.");
            }

            // Get the role name of the current user
            String roleName = currentUser.getRoleId().getRoleName();

            // If the user is an admin
            if(roleName.equalsIgnoreCase("admin")){
                // Get the question ID from the request parameter
                int id = Integer.parseInt(request.getParameter("id"));
                // Delete the question
                questionService.delete(id);

                // Get the list of all questions
                List<Question> questionList = questionService.findAll();
                // Set the list of questions as an attribute of the request
                request.setAttribute("questionList", questionList);

                // Forward the request to the admin questions page
                String path = "/WEB-INF/pages/cms/adminquestions.jsp";
                request.getRequestDispatcher(path).forward(request, response);

                // If the user is a regular user
            } else if(roleName.equalsIgnoreCase("user")){
                // Get the question ID from the request parameter
                int id = Integer.parseInt(request.getParameter("id"));
                // Delete the question
                questionService.delete(id);

                // Get the list of questions created by the current user
                List<Question> questionList = questionService.findByUserId(currentUser.getUserId());
                // Set the list of questions as an attribute of the request
                request.setAttribute("questionList", questionList);

                // Forward the request to the admin questions page
                String path = "/WEB-INF/pages/cms/adminquestions.jsp";
                request.getRequestDispatcher(path).forward(request, response);

                // If the user has an invalid role
            } else{
                throw new Exception("Invalid role");
            }
            // If an exception occurs
        } catch (Exception e) {
            // Redirect to the login page
            response.sendRedirect("../login.html");
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
        return "Servlet which processes the deletion of questions from the admin panel. It checks the user's role and only allows admins or the question's owner to delete it. After deletion, it forwards the request to the admin questions page.";
    }// </editor-fold>

}
