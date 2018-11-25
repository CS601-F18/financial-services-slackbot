package cs601.sideproject.application;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/** 
 * Hits the https://slack.com/api/rtm.connect endpoint. 
 * Bot will message user.
 * 
 * **/
public class RealTimeMessaging extends HttpServlet{
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
    		checkChallenge(request, response);
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("Length" + request.getContentLength());
        System.out.println("Input: " + body);
        System.out.println("text" + request.getAttribute("text"));
        System.out.println(request.getQueryString());
        System.out.println(request.getParameter("Token"));
    }
    
    private String parseText(String text) {
    		String output = "";
    		return output;
    }
    
    private void checkChallenge(HttpServletRequest request, HttpServletResponse response) throws IOException {
    		String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonParser parser = new JsonParser();
        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
        String challenge;
        response.setContentType("application/x-www-form-urlencoded");
        response.setStatus(HttpServletResponse.SC_OK);
        /* Check for challenge token */
        if (jsonBody.get("challenge") != null) {
			challenge = jsonBody.get("challenge").getAsString();
			response.getWriter().println(challenge);
			System.out.println("challenge" + request.getAttribute("challenge"));
        }
    }
}
