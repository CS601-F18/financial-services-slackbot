package cs601.sideproject.authentication;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cs601.sideproject.application.Constants;
import cs601.sideproject.database.Database;
import cs601.sideproject.database.User;

/**
 * Oauth Authentication is a two part process. After the first part 
 * of Oauth Confirmation is called, this endpoint is hit. This also
 * serves all of the logged in data (stocks owned and transaction history)
 * @author nkebbas
 *
 */
public class Dashboard extends HttpServlet {
	private String username;
	
	public Dashboard() {
		this.username = "";
	}
	
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
		System.out.println("here");
		/* Must Receive from Signin */
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getParameter("code");
        /* Not sure if this is working correctly */
        /* Send with code to finish authentication */
        URL url = new URL("https://slack.com/api/oauth.access?code=" + request.getParameter("code") + "&client_id=" +Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&redirect_uri=" + Constants.REDIRECT);
        HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
        connect.getResponseCode();
        /* Get the db connection to add the user info */
	    Database db = Database.getInstance();
        InputStream in = connect.getInputStream();
        String encoding = connect.getContentEncoding();
        String body = IOUtils.toString(in, encoding);
        /* Now get all that JSON Data because we need to save it */
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonBody = (JsonObject) jsonParser.parse(body);
        User user = createUser(jsonBody);
        
        /* If they have the token, check DB for which user they are and log them in */

        		if (authenticateBySlackId(db, jsonBody)) {
        			System.out.println("authenticated by slack Id");
        			JsonObject userObject = (JsonObject) jsonBody.get("user");
				displayDashboard(response, userObject, db);
        		} else {
        			if (user != null) {
	    	    		    try {
	    	    				db.getDBManager().createUser(user, "spusers");
	    	    				JsonObject userObject = (JsonObject) jsonBody.get("user");
	    	    				displayDashboard(response, userObject, db);
	    	    			} catch (SQLException e) {
	    	    				e.printStackTrace();
	    	    			}
	    	    	    } else {
	    	    	    		response.getOutputStream().print("<p>Bot successfully added to workspace. Please <a href=\"\\'\"/signin\"\\'\">login to access your dashboard.</p>");
	    	    	    }
        			
        		}
        }
	        
        /* Check DB for existing users and check for null first */
    private Cookie addUserNameCookie(String username) {
    		String[]splitName = username.split(" ");
     	Cookie cookie = new Cookie("username", splitName[0]);
     	return cookie;
    }
	
    private User createUser(JsonObject jsonBody) {
        Gson g = new Gson();
        User user  = g.fromJson(jsonBody.get("user"), User.class);
        return user;
    }
    
    
    /* See if the user has the correct token */
    private boolean authenticateBySlackId(Database db, JsonObject jsonBody) {
		if(jsonBody.get("user") == null) {
			return false;
		}
    		try {
			if (db.getDBManager().authenticate(jsonBody.get("user").getAsJsonObject().get("id").getAsString())) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    		return false;
    }
    
    private void displayDashboard(HttpServletResponse response, JsonObject userObject, Database db) throws IOException {
		String userId = userObject.get("id").getAsString();
		this.username = userObject.get("name").getAsString();
	    Cookie usernameCookie = addUserNameCookie(this.username);
		response.addCookie(usernameCookie);
	    response.getOutputStream().println("<h1> Hi " + this.username + "</h1>");
	    response.getOutputStream().println("<h3> Welcome to your dashboard</h3>");
	    response.getOutputStream().println("<h4>Transactions</h4>");
		try {
			response.getOutputStream().println(db.getDBManager().getAllUserTransactions(userId));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getOutputStream().println("<h4>Stocks</h4>");
		try {
			response.getOutputStream().println(db.getDBManager().getAllStocksOwned(userId, "stockown"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}