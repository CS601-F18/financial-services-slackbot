package cs601.sideproject.application.events;

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
 * Acts as an API endpoint for slack events. If URL of this is changed, it must be updated in slack.
 * @author nkebbas
 *
 */
public class EventHandler extends HttpServlet {
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
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
