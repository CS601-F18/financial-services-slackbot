package cs601.project4.slackbot;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TransactionHandler extends HttpServlet{
	private Map<String, String> arguments;
	
	public TransactionHandler() {
		arguments = new HashMap<String, String>();
	}
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
        /* Cite: https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest */
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        System.out.println("post request");
        System.out.println("Length" + request.getContentLength());
        String[] parameters = test.split("&");
        for (String i: parameters) {
        	System.out.println(i);
        		String[] splitArguments = i.split("=");
        		arguments.put(splitArguments[0], splitArguments[1]);
        }
        System.out.println("Input: " + test);
        System.out.println("text: " + arguments.get("text"));
        
        /* Now save the text to the database */
    }
    
    private String parseText(String text) {
    		String output = "";
    		return output;
    }
}
