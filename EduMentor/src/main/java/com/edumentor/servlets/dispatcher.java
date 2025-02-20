package com.edumentor.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet to dispatch requests to the appropriate servlets based on the URL path.
 * Acts as a central point for routing requests.
 *
 * @author adrian
 *
 */
public class dispatcher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * <p>This method retrieves the servlet path from the request and dispatches the request to the appropriate servlet
     * based on the defined URL mappings. If no matching URL is found, it forwards the request to the error page.</p>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Initialize the path to an empty string
        String path = "";
        // Get the servlet path from the request
        String pathRequest = request.getServletPath();

        // Switch statement to determine the appropriate servlet based on the URL path
        switch(pathRequest){
            // If the URL is "/login.html", dispatch to the showloginserv servlet
            case "/login.html" : path = "/showloginserv"; break;
            // If the URL is "/about.html", dispatch to the aboutserv servlet
            case "/about.html" : path = "/aboutserv"; break;

            // If the URL is "/posts.html", dispatch to the viewpostsserv servlet
            case "/posts.html" : path = "/viewpostsserv"; break;
            // If the URL is "/view-post.html", dispatch to the viewpostserv servlet
            case "/view-post.html" : path = "/viewpostserv"; break;
            // If the URL is "/posts-category.html", dispatch to the viewpostscategoryserv servlet
            case "/posts-category.html" : path = "/viewpostscategoryserv"; break;

            // If the URL is "/questions.html", dispatch to the viewquestionsserv servlet
            case "/questions.html" : path = "/viewquestionsserv"; break;
            // If the URL is "/view-question.html", dispatch to the viewquestionserv servlet
            case "/view-question.html" : path = "/viewquestionserv"; break;
            // If the URL is "/questions-category.html", dispatch to the viewquestionscategoryserv
            case "/questions-category.html" : path = "/viewquestionscategoryserv"; break;

            // If the URL is "/cms/index.html", dispatch to the cmshomeserv servlet
            case "/cms/index.html" : path = "/cms/cmshomeserv"; break;

            // If the URL is "/cms/profile.html", dispatch to the cmsprofileserv servlet
            case "/cms/profile.html" : path = "/cms/cmsprofileserv"; break;

            // If the URL is "/cms/questions.html", dispatch to the cmsviewquestionsserv servlet
            case "/cms/questions.html" : path = "/cms/cmsviewquestionsserv"; break;
            // If the URL is "/cms/view-question.html", dispatch to the cmsviewquestionsserv servlet
            case "/cms/view-question.html" : path = "/cms/cmsviewquestionserv"; break;
            // If the URL is "/cms/questions-category.html", dispatch to the cmsviewquestionscategoryserv servlet
            case "/cms/questions-category.html" : path = "/cms/cmsviewquestionscategoryserv"; break;

            // If the URL is "/cms/posts.html", dispatch to the cmsviewpostsserv servlet
            case "/cms/posts.html" : path = "/cms/cmsviewpostsserv"; break;
            // If the URL is "/cms/view-post.html", dispatch to the cmsviewpostserv servlet
            case "/cms/view-post.html" : path = "/cms/cmsviewpostserv"; break;
            // If the URL is "/cms/posts-category.html", dispatch to the cmsviewpostscategoryserv servlet
            case "/cms/posts-category.html" : path = "/cms/cmsviewpostscategoryserv"; break;

            // If the URL is "/cms/add-post.html", dispatch to the cmsshowaddpostserv servlet
            case "/cms/add-post.html" : path = "/cms/cmsshowaddpostserv"; break;
            // If the URL is "/cms/add-question.html", dispatch to the cmsshowaddquestionserv servlet
            case "/cms/add-question.html" : path = "/cms/cmsshowaddquestionserv"; break;

            // If the URL is "/admin/posts.html", dispatch to the adminpostsserv servlet
            case "/admin/posts.html" : path="/admin/adminpostsserv"; break;
            // If the URL is "/admin/delete-post.html", dispatch to the adminpostdeleteserv servlet
            case "/admin/delete-post.html" : path="/admin/adminpostdeleteserv"; break;

            // If the URL is "/admin/questions.html", dispatch to the adminquestionsserv servlet
            case "/admin/questions.html" : path="/admin/adminquestionsserv"; break;
            // If the URL is "/admin/delete-question.html", dispatch to the adminquestiondeleteserv servlet
            case "/admin/delete-question.html" : path="/admin/adminquestiondeleteserv"; break;

            // If the URL is "/admin/users.html", dispatch to the adminusersserv servlet
            case "/admin/users.html" : path="/admin/adminusersserv"; break;
            // If the URL is "/admin/delete-user.html", dispatch to the adminuserdeleteserv servlet
            case "/admin/delete-user.html" : path="/admin/adminuserdeleteserv"; break;

            // If the URL is "/cms/logout.html", dispatch to the cmslogoutserv servlet
            case "/cms/logout.html" : path = "/cms/cmslogoutserv"; break;

            // If no matching URL is found, dispatch to the error page
            default : path = "WEB-INF/pages/error.jsp"; break;
        }

        // Forward the request to the appropriate servlet
        request.getRequestDispatcher(path).forward(request, response);

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
        return "Servlet to dispatch requests to the appropriate servlets based on the URL path.";
    }// </editor-fold>

}
