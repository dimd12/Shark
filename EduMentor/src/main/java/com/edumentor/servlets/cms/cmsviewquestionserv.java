package com.edumentor.servlets.cms;

import com.edumentor.models.Answer;
import com.edumentor.models.Category;
import com.edumentor.models.Question;
import com.edumentor.models.User;
import com.edumentor.services.AnswerServiceIntf;
import com.edumentor.services.CategoryServiceIntf;
import com.edumentor.services.QuestionServiceIntf;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.AnswerServiceImpl;
import com.edumentor.services.impl.CategoryServiceImpl;
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
 * Servlet to display a specific question and its answers in the CMS.
 * Retrieves question details and associated answers to be displayed on the view question page.
 *
 * @author adrian
 */
@WebServlet(name = "cmsviewquestionserv", urlPatterns = {"/cms/cmsviewquestionserv"})
public class cmsviewquestionserv extends HttpServlet {

    QuestionServiceIntf questionService = QuestionServiceImpl.getInstance();
    AnswerServiceIntf answerService = AnswerServiceImpl.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, retrieves the question by its ID using {@link QuestionServiceIntf},
     * retrieves the answers for the question using {@link AnswerServiceIntf}, sets the user, question, and answer list as request attributes,
     * and forwards the request to the view question page.</p>
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

            // Get the question ID from the request parameter
            int id = Integer.parseInt(request.getParameter("id"));
            // Find the question by ID
            Question question = questionService.findById(id);
            // Set the question object as an attribute of the request
            request.setAttribute("question", question);

            // Find the answers for the question
            List<Answer> answerList = answerService.findByQuestionId(id);
            // Set the list of answers as an attribute of the request
            request.setAttribute("answerList", answerList);

            // Forward the request to the view question page
            String path = "/WEB-INF/pages/cms/cmsviewquestion.jsp";
            request.getRequestDispatcher(path).forward(request, response);

            // Catch any exceptions that occur during the process
        } catch (Exception ex) {
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
        return "Servlet to display a specific question and its answers in the CMS. Retrieves question details and associated answers to be displayed on the view question page.";
    }// </editor-fold>

}
