package com.edumentor.servlets.cms;

import com.edumentor.models.Category;
import com.edumentor.models.Post;
import com.edumentor.models.User;
import com.edumentor.services.CategoryServiceIntf;
import com.edumentor.services.PostServiceIntf;
import com.edumentor.services.UserServiceIntf;
import com.edumentor.services.impl.CategoryServiceImpl;
import com.edumentor.services.impl.PostServiceImpl;
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
 * Servlet that retrieves and displays posts in the admin panel, based on user role.
 * Admins see all posts, while regular users see only their own posts.
 *
 * @author adrian
 */
@WebServlet(name = "adminpostsserv", urlPatterns = {"/admin/adminpostsserv"})
public class adminpostsserv extends HttpServlet {

    PostServiceIntf postService = PostServiceImpl.getInstance();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves the user's session, verifies the user's role, and retrieves the appropriate list of posts
     * (all posts for admins, user's own posts for regular users). It then sets the post list and category list as request
     * attributes and forwards the request to the admin posts page.</p>
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
            // Find the current user object by username
            User currentUserObj = userService.findByUsername(currentUser);

            // Get the full user object by user ID
            User fullUser = userService.findById(currentUserObj.getUserId());

            // If the current user object is null, throw an exception
            if (currentUserObj == null) {
                throw new Exception("The user is null");
            }

            // If the user's role ID is null or the role name is null, throw an exception
            if (currentUserObj.getRoleId() == null || currentUserObj.getRoleId().getRoleName() == null) {
                throw new Exception("User role is not defined.");
            }

            // Get the role name of the current user
            String roleName = currentUserObj.getRoleId().getRoleName();

            // If the user is an admin
            if(roleName.equalsIgnoreCase("admin")){

                // Get the list of all posts
                List<Post> postList = postService.findAll();
                // Set the list of posts as an attribute of the request
                request.setAttribute("postList", postList);

                // Get the category service implementation
                CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
                // Get the list of all categories
                List<Category> categoryList = categoryService.findAll();
                // Set the list of categories as an attribute of the request
                request.setAttribute("categoryList", categoryList);

                // Forward the request to the admin posts page
                String path = "/WEB-INF/pages/cms/adminposts.jsp";
                request.getRequestDispatcher(path).forward(request, response);

                // If the user is a regular user
            } else if(roleName.equalsIgnoreCase("user")){

                // Get the list of posts created by the current user
                List<Post> postList = postService.findByUserId(currentUserObj.getUserId());
                // Set the list of posts as an attribute of the request
                request.setAttribute("postList", postList);

                // Get the category service implementation
                CategoryServiceIntf categoryService = CategoryServiceImpl.getInstance();
                // Get the list of all categories
                List<Category> categoryList = categoryService.findAll();
                // Set the list of categories as an attribute of the request
                request.setAttribute("categoryList", categoryList);

                // Forward the request to the admin posts page
                String path = "/WEB-INF/pages/cms/adminposts.jsp";
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
        return "Shows the admin posts page. Admins see all posts, while regular users see only their own posts. ";
    }// </editor-fold>

}
