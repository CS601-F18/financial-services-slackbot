package cs601.project4.slackbot;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Connect to https://slack.com/api/rtm.connect */
public class RealTimeMessaging extends HttpServlet{
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        System.out.println(request.toString());
        URL url;
        url = new URL(Constants.API_DESTINATION_RTM + "?token=" + Constants.BOT_TOKEN);
        HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
        connect.getResponseCode();
        System.out.println("get request" + url);
        System.out.println(connect.getResponseCode());
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
    
    private String parseText(String text) {
    		String output = "";
    		return output;
    }
}
