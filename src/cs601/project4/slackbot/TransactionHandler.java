package cs601.project4.slackbot;

import java.io.IOException;
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

import cs601.database.Database;
import cs601.database.Transaction;
import cs601.project4.slackbot.Constants;

/**
 * Required syntax (add): /transaction "charge $5 for ice cream"
 * Required syntax (subtract) /transaction "credit $5"
 * @author nkebbas
 *
 */
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
        request.getAttribute("text");
        /* Cite: https://stackoverflow.com/questions/8100634/get-the-post-request-body-from-httpservletrequest */
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] parameters = test.split("&");
        for (String i: parameters) {
        		String[] splitArguments = i.split("=");
        		arguments.put(splitArguments[0], splitArguments[1]);
        }
        response.getWriter().println("You said: " + arguments.get("text"));
        parseTextIntoHashMap(arguments.get("text"));
        Transaction t = new Transaction(arguments.get("user_id"), Float.parseFloat(arguments.get("value")), arguments.get("operation"));
        
        /* Get the db connection to add the user info */
	    Database db = Database.getInstance();
        
	    try {
			db.getDBManager().createTransaction(t, "transactions");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
       // response.sendRedirect("https://slack.com/api/oauth.access?code=" + request.getParameter("code") + "&client_id=" +Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&redirect_uri=" + Constants.REDIRECT);
	   System.out.println(test);
       System.out.println("transaction handler hit");
        /* Now save the text to the database */   
    }
    
    private void parseTextIntoHashMap(String text) {
    		String[] values = text.split("%24");
    		this.arguments.put("operation", values[0]);
    		String[] output = values[1].split("\\+");
    		this.arguments.put("value", output[0]);
    		String description = "";
    		for (int i = 1; i < output.length; i++) {
    			description += output[i];
    			description += " ";
    		}
    		this.arguments.put("description", description);
    }
}
