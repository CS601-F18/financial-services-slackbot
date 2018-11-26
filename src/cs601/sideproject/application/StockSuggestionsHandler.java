package cs601.sideproject.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.sideproject.database.Database;

/**
 * User is going to type /suggestions, then slackbot is going to query the DB 
 * and look for past stocks searched by that user.
 * 
 * @author nkebbas
 *
 */
public class StockSuggestionsHandler extends HttpServlet {
	private Map<String, String> arguments;
	
	public StockSuggestionsHandler() {
		arguments = new HashMap<String, String>();
	}
	
	protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
		String userId = "";
		/* Get stock first */
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getAttribute("text");
        /* Cite: https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest */
        String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] parameters = text.split("&");
        for (String i: parameters) {
        		
        		System.out.println(i);
        		String[] splitArguments = i.split("=");
        		if (splitArguments.length > 1) {
        			arguments.put(splitArguments[0], splitArguments[1]);	
        		}
        }
        userId = arguments.get("text");
        String botResponse = "Past searches include: \n";
	    
        /* Then get Data from database */
	    
	    Database db = Database.getInstance();
	    try {
			botResponse += db.getDBManager().returnSuggestions(arguments.get("user_id"), "stocks");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    response.getWriter().write(botResponse);
   }
}
