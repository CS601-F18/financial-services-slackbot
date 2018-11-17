package cs601.project4.slackbot;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SignInHandler extends HttpServlet {
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(""
        		+ "<a href=\"https://slack.com/oauth/authorize?scope=identity.basic&client_id=" + Constants.CLIENT_ID + "&redirect_uri=" + Constants.REDIRECT+ "\">"
        		+ "<img src=\"https://api.slack.com/img/sign_in_with_slack.png\" />"
        		+ "</a>");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {

    }
}
