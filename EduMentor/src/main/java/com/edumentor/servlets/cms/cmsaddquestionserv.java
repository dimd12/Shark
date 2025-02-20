package com.edumentor.servlets.cms;

import com.edumentor.models.Category;
import com.edumentor.models.Question;
import com.edumentor.models.User;
import com.edumentor.services.CategoryServiceIntf;
import com.edumentor.services.QuestionServiceIntf;
import com.edumentor.services.UserServiceIntf;
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
import java.util.Calendar;
import java.util.List;

/**
 * Servlet to handle adding a new question from the CMS.
 * It retrieves user information, validates input, and saves the question to the database.
 *
 * @author adrian
 */
@WebServlet(name = "cmsaddquestionserv", urlPatterns = {"/cms/cmsaddquestionserv"})
public class cmsaddquestionserv extends HttpServlet {

    QuestionServiceIntf questionService = QuestionServiceImpl.getInstance();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, validates the input parameters (title and category),
     * creates a new {@link Question} object, and saves it to the database using {@link QuestionServiceIntf}.
     * If any error occurs during the process, it sets an appropriate message in the request and forwards it back to the add question page.</p>
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
            String title = request.getParameter("title");
            String details = request.getParameter("details");
            String imageUrl = request.getParameter("imageUrl");
            String categoryId = request.getParameter("category");

            // Validate the request parameters
            if(title == null || title.isEmpty() ||
                    categoryId == null || categoryId.isEmpty()){
                // If any of the required parameters is missing, set an error message and forward back to the add question page
                request.setAttribute("message", "Please fill all the fields");
                request.getRequestDispatcher("WEB-INF/pages/cms/cmsaddquestion.jsp").forward(request, response);
                return;
            }

            // Get the selected category
            Category selectedCategory = null;
            try {
                // Parse the category ID from the request parameter
                int categoryIdInt = Integer.parseInt(categoryId);
                // Find the category by ID
                selectedCategory = categoryService.findById(categoryIdInt);

                // If the category is null, throw an exception
                if (selectedCategory == null) {
                    throw new Exception("Invalid category selected");
                }

                // Catch any exceptions that occur during the process
            } catch (NumberFormatException e) {
                // If the category ID is not a number, set an error message and forward back to the add question page
                request.setAttribute("message", "Invalid category ID format.");
                request.getRequestDispatcher("WEB-INF/pages/cms/cmsaddquestion.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                // If any other exception occurs, set an error message and forward back to the add question page
                request.setAttribute("message", "Error finding category: " + e.getMessage());
                request.getRequestDispatcher("WEB-INF/pages/cms/cmsaddquestion.jsp").forward(request, response);
                return;
            }

            // Create a new question object
            Question question = new Question();
            // Set the question properties
            question.setTitle(title);
            question.setDetails(details);
            question.setImageUrl(imageUrl);

            // Create a new date object
            Calendar calendar = Calendar.getInstance();
            java.sql.Date ourJavaDateObject = new java.sql.Date(calendar.getTime().getTime());
            question.setDateCreated(ourJavaDateObject);

            question.setCategoryId(selectedCategory);
            question.setUserId(user);

            try{
                // Save the question to the database
                questionService.save(question);
                // Redirect to the questions page
                response.sendRedirect("/admin/questions.html");
                // Catch any exceptions that occur during the process
            } catch(Exception ex){
                // If any exception occurs, set an error message and forward back to the add question page
                request.setAttribute("message", "Error saving post: " + ex.getMessage());
                request.getRequestDispatcher("/WEB-INF/pages/cms/cmsaddquestion.jsp").forward(request, response);
                return;
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
        return "Servlet which is responsible for creating a question only for users with the CMS role.";
    }// </editor-fold>

}
