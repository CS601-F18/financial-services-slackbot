package cs601.sideproject.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Page for adding the slackbot to a workspace
 * @author nkebbas
 *
 */
public class Slackbot extends HttpServlet {

    protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(""
        		+ "<a href=\"https://slack.com/oauth/authorize?scope=identity.basic&client_id=" + Constants.CLIENT_ID + "\">"
        		+ "<img src=\"https://api.slack.com/img/sign_in_with_slack.png\" />"
        		+ "</a>");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"ok\"}");
    }
}
