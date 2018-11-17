package cs601.project4.slackbot;

import java.io.IOException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EventHandler extends HttpServlet {
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
        String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JsonParser parser = new JsonParser();
        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
        System.out.println(jsonBody);
        String challenge;
        response.setContentType("application/x-www-form-urlencoded");
        response.setStatus(HttpServletResponse.SC_OK);
        /* Check for challenge token */
        if (jsonBody.get("challenge") != null) {
        		challenge = jsonBody.get("challenge").getAsString();
             response.getWriter().println(challenge);
             System.out.println("challenge" + request.getAttribute("challenge"));
        }
        
        // if (jsonBody.get(""))
        



    }
}
