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

import java.io.IOException;
import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet to handle posting answers to a question in the CMS.
 * It retrieves user information, validates input, and saves the answer to the database.
 *
 * @author adrian
 */
@WebServlet(name = "cmsquestionanswerserv", urlPatterns = {"/cms/cmsquestionanswerserv"})
public class cmsquestionanswerserv extends HttpServlet {

    QuestionServiceIntf questionService = QuestionServiceImpl.getInstance();
    AnswerServiceIntf answerService = AnswerServiceImpl.getInstance();


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, validates the input parameters (question ID and answer response),
     * creates a new {@link Answer} object, and saves it to the database using {@link AnswerServiceIntf}.
     * If any error occurs during the process, it sets an appropriate message in the request and forwards it back to the view question page.</p>
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

            // Get the category service implementation
            CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
            // Get the list of all categories
            List<Category> categoryList = categoryService.findAll();
            // Set the list of categories as an attribute of the request
            request.setAttribute("categoryList", categoryList);

            // Set the user object as an attribute of the request
            request.setAttribute("user", user);

            // Get the request parameters
            String questionIdStr = request.getParameter("questionId");
            String questionResponse = request.getParameter("response");
            String imageUrl = request.getParameter("imageUrl");

            // Validate the request parameters
            if (questionIdStr == null || questionIdStr.isEmpty() || questionResponse == null || questionResponse.isEmpty()) {
                // If any of the required parameters is missing, set an error message and forward back to the view question page
                request.setAttribute("message", "Question ID and Response Text are required.");
                String questionViewUrl = "/cms/view-question.html?id=" + questionIdStr;
                request.getRequestDispatcher(questionViewUrl).forward(request, response);
                return;
            }

            try{
                // Parse the question ID from the request parameter
                Integer questionId = Integer.parseInt(questionIdStr);
                // Find the question by ID
                Question question = questionService.findById(questionId);
                // If the question is null
                if(question == null){
                    // Set an error message and forward back to the view question page
                    request.setAttribute("message", "Question not found.");
                    String questionViewUrl = "/cms/view-question.html?id=" + questionIdStr;
                    request.getRequestDispatcher(questionViewUrl).forward(request, response);
                    return;
                }

                // Create a new answer object
                Answer answer = new Answer();
                // Set the answer properties
                answer.setQuestionId(question);
                answer.setUserId(user);
                answer.setResponse(questionResponse);
                answer.setImageUrl(imageUrl);

                // Create a new date object
                java.util.Date utilDate = Calendar.getInstance().getTime();
                Date sqlDate = new Date(utilDate.getTime());
                answer.setDateCreated(sqlDate);

                // Save the answer to the database
                answerService.save(answer);
                // Redirect to the view question page
                response.sendRedirect("/cms/view-question.html?id=" + questionId);
                // Catch any exceptions that occur during the process
            } catch (NumberFormatException e) {
                // If the question ID is not a number, set an error message and forward back to the view question page
                request.setAttribute("message", "Invalid Question ID format.");
                String questionViewUrl = "/cms/view-question.html?id=" + questionIdStr;
                request.getRequestDispatcher(questionViewUrl).forward(request, response);
            } catch (Exception e) {
                // If any other exception occurs, set an error message and forward back to the view question page
                request.setAttribute("message", "Error saving answer: " + e.getMessage());
                String questionViewUrl = "/cms/view-question.html?id=" + questionIdStr;
                request.getRequestDispatcher(questionViewUrl).forward(request, response);
            }
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
        return "Servlet for adding an answer to a question.";
    }// </editor-fold>

}
