package cs601.project4.slackbot;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeHandler extends HttpServlet {
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Home</h1>"
        		+ "<a href=\"https://slack.com/oauth/authorize?scope=identity.basic&client_id=" + Constants.CLIENT_ID + "\">"
        		+ "<img src=\"https://api.slack.com/img/sign_in_with_slack.png\" />"
        		+ "</a>");
        System.out.println("Home handler hit");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("Hi Im michael");
        request.getAttribute("text");
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("post request");
        System.out.println("Length" + request.getContentLength());
        System.out.println("Input: " + test);
        System.out.println("text" + request.getAttribute("text"));
        System.out.println(request.getQueryString());
        System.out.println(request.getParameter("Token"));
    }
}
