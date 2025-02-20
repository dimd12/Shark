package com.edumentor.servlets.cms;

import com.edumentor.models.*;
import com.edumentor.services.*;
import com.edumentor.services.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

/**
 * Servlet to handle posting reviews for a post in the CMS.
 * It retrieves user information, validates input, and saves the review to the database.
 *
 * @author adrian
 */
@WebServlet(name = "cmspostreviewserv", urlPatterns = {"/cms/cmspostreviewserv"})
public class cmspostreviewserv extends HttpServlet {

    PostServiceIntf postService = PostServiceImpl.getInstance();
    ReviewServiceIntf reviewService = ReviewServiceImpl.getInstance();


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves user information from the session, validates the input parameters (post ID, rating and review message),
     * creates a new {@link Review} object, and saves it to the database using {@link ReviewServiceIntf}.
     * If any error occurs during the process, it sets an appropriate message in the request and forwards it back to the view post page.</p>
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
            String postIdStr = request.getParameter("postId");
            String ratingStr = request.getParameter("rating");
            String reviewMessage = request.getParameter("reviewMessage");

            // Validate the request parameters
            if (postIdStr == null || postIdStr.isEmpty() || ratingStr == null || ratingStr.isEmpty() || reviewMessage == null || reviewMessage.isEmpty() ) {
                // If any of the required parameters is missing, set an error message and forward back to the view post page
                request.setAttribute("message", "Post ID and Response Text are required.");
                String questionViewUrl = "/cms/view-post.html?id=" + postIdStr;
                request.getRequestDispatcher(questionViewUrl).forward(request, response);
                return;
            }

            try{
                // Parse the post ID from the request parameter
                Integer postId = Integer.parseInt(postIdStr);
                // Find the post by ID
                Post post = postService.findById(postId);
                // If the post is null
                if(post == null){
                    // Set an error message and forward back to the view post page
                    request.setAttribute("message", "Post not found.");
                    String questionViewUrl = "/cms/view-post.html?id=" + postIdStr;
                    request.getRequestDispatcher(questionViewUrl).forward(request, response);
                    return;
                }

                // Create a new review object
                Review review = new Review();
                // Set the review properties
                review.setRating(Integer.parseInt(ratingStr));
                review.setUserId(user);
                review.setPostId(post);
                review.setReviewMessage(reviewMessage);

                // Create a new date object
                java.util.Date utilDate = Calendar.getInstance().getTime();
                Date sqlDate = new Date(utilDate.getTime());
                review.setDateSent(sqlDate);

                // Save the review to the database
                reviewService.save(review);
                // Redirect to the view post page
                response.sendRedirect("/cms/view-post.html?id=" + postIdStr);
                // Catch any exceptions that occur during the process
            } catch (NumberFormatException e) {
                // If the post ID is not a number, set an error message and forward back to the view post page
                request.setAttribute("message", "Invalid Post ID format.");
                String postViewUrl = "/cms/view-post.html?id=" + postIdStr;
                request.getRequestDispatcher(postViewUrl).forward(request, response);
            } catch (Exception e) {
                // If any other exception occurs, set an error message and forward back to the view post page
                request.setAttribute("message", "Error saving review: " + e.getMessage());
                String postViewUrl = "/cms/view-post.html?id=" + postIdStr;
                request.getRequestDispatcher(postViewUrl).forward(request, response);
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
        return "Servlet responsible for posting reviews for a post in the CMS. It retrieves user information, validates input, and saves the review to the database. ";
    }// </editor-fold>

}
