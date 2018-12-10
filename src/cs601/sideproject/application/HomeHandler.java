package cs601.sideproject.application;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet for the home page
 * @author nkebbas
 *
 */
public class HomeHandler extends HttpServlet {
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Welcome to your Financial Services Site!</h1>"
        		+ "<a href=\"https://slack.com/oauth/authorize?scope=identity.basic&client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.REDIRECT+ "\">"
        		+ "<img src=\"https://api.slack.com/img/sign_in_with_slack.png\" />"
        		+ "</a>");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getAttribute("text");
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

    }
}
