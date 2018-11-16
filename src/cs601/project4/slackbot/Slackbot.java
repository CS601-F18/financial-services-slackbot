package cs601.project4.slackbot;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Slackbot extends HttpServlet {

    protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(""
        		+ "<a href=\"https://slack.com/oauth/authorize?"
        		+ "scope=incoming-webhook,commands,bot&client_id=378520430422.465486620272&redirect_uri=https://slack.com/oauth/authorize\">"
        		+ "<img alt=\"Add to Slack\" height=\"40\" width=\"139\" src=\"https://platform.slack-edge.com/img/add_to_slack.png\" "
        		+ "srcset=\"https://platform.slack-edge.com/img/add_to_slack.png 1x, "
        		+ "https://platform.slack-edge.com/img/add_to_slack@2x.png 2x\" /></a>");
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
