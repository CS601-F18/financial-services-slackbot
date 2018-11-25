package cs601.sideproject.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This authenticates the app to retrieve the authentication token necessary 
 * for the bot to work in the workspace. 
 * @author nkebbas
 *
 */
public class OauthConfirm extends HttpServlet {
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println(response.toString());
        response.sendRedirect("https://slack.com/oauth/authorize?client_id=" + Constants.CLIENT_ID + "&scope=" + Constants.SCOPE + "&redirect_uri=" + Constants.REDIRECT);
        System.out.println("Oauth 1 hit");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"ok\"}");
        System.out.println("post request");
        System.out.println(request.getContentLength());
        System.out.println(request.getQueryString());
        System.out.println(request.getParameter("Token"));
    }
}
