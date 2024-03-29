package cs601.sideproject.application.transaction;

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

import cs601.sideproject.application.Constants;
import cs601.sideproject.database.Database;

/**
 * Required syntax (add): /transaction "charge $5 for ice cream"
 * Required syntax (subtract) /transaction "credit $5"
 * Logs user's transactions to database
 * @author nkebbas
 *
 */
public class TransactionHandler extends HttpServlet {
	private Map<String, String> arguments;
	
	public TransactionHandler() {
		arguments = new HashMap<String, String>();
	}
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getAttribute("text");
        String text = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String[] parameters = text.split("&");
        for (String i: parameters) {
        		String[] splitArguments = i.split("=");
        		arguments.put(splitArguments[0], splitArguments[1]);
        }
        response.getWriter().println("Transaction logged!");
        if (parseTextIntoHashMap(arguments.get("text"))) {
        	 	Transaction t = new Transaction(arguments.get("user_id"), Float.parseFloat(arguments.get("value")), arguments.get("operation"), arguments.get("description"));
            /* Now save the text to the database */   
		    Database db = Database.getInstance();
		    try {
				db.getDBManager().createTransaction(t, "transactions");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }
    
    private boolean parseTextIntoHashMap(String text) {
    		String[] values = text.split("%24");
    		if (values.length > 1) {
	    		this.arguments.put("operation", values[0]);
	    		System.out.println(values[0]);
	    		String[] output = values[1].split("\\+");
	    		this.arguments.put("value", output[0]);
	    		String description = "";
	    		for (int i = 1; i < output.length; i++) {
	    			description += output[i];
	    			description += " ";
	    		}
	    		this.arguments.put("description", description);
	    		return true;
    		}
    		return false;
    }
}
