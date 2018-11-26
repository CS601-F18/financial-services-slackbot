package cs601.sideproject.application;

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
 * of Oauth Confirmation is called, this endpoint is hit.
 * @author nkebbas
 *
 */
public class OauthConfirmStep2 extends HttpServlet {
	
	protected void doGet( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        request.getParameter("code");
        System.out.println(request.getParameter("code"));
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
        if(checkForToken(request)) {
        	System.out.println("check for token");
        	System.out.println(jsonBody);
        		if (authenticateBySlackId(db, jsonBody)) {
        			System.out.println("already authenticated");
        			try {
        				JsonObject userObject = (JsonObject) jsonBody.get("user");
        				String userId = userObject.get("id").getAsString();
        				response.getOutputStream().println(db.getDBManager().getAllUserTransactions(userId));
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		} else {
        			if (user != null) {
	    	    		    try {
	    	    				db.getDBManager().createUser(user, "spusers");
	    	    				JsonObject userObject = (JsonObject) jsonBody.get("user");
	            				String userId = userObject.get("id").getAsString();
	            				response.getOutputStream().println(db.getDBManager().getAllUserTransactions(userId));
	    	    			} catch (SQLException e) {
	    	    				// TODO Auto-generated catch block
	    	    				e.printStackTrace();
	    	    			}
	    	    	    } if (jsonBody.get("access_token") != null) {
	    	        		String accessToken = jsonBody.get("access_token").getAsString();
	    	        		user.setAccessToken(accessToken);
	    	    	        Cookie cookie = addAccessTokenCookie(accessToken);
	    	    	        response.addCookie(cookie);
    	        		}
        		}
        }
	        
        /* Check DB for existing users and check for null first */
    	}
    
	
    private User createUser(JsonObject jsonBody) {
        Gson g = new Gson();
        User user  = g.fromJson(jsonBody.get("user"), User.class);
        return user;
    }
    /* Add Cookie with Access Token */
    private Cookie addAccessTokenCookie(String accessToken) {
	    	Cookie cookie = new Cookie("token", accessToken);
	    	return cookie;
    }
    
    /* See if they have a token cookie */
    private boolean checkForToken(HttpServletRequest request) {
    	  Cookie[] cookies = request.getCookies();
    	  if(cookies != null) {
    	      for (int i = 0; i < cookies.length; i++) {
    	          Cookie cookie=cookies[i];
    	          if (cookie.getName().equals("token")){
    	        	  	return true;
    	          }
    	       }
    	   }
    	  return false;
    }
    
    /* See if the user has the correct token */
    private boolean authenticateBySlackId(Database db, JsonObject jsonBody) {
		if(jsonBody.get("user") == null) {
			System.out.println("user is null");
			return false;
		}
    		try {
			if (db.getDBManager().authenticate(jsonBody.get("user").getAsJsonObject().get("id").getAsString())) {
				System.out.println("true");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    		return false;
    }
    
    /* If user can't authenticate by cookie, try authenticating by User ID */
    private boolean authenticateByLogin(Database db, JsonObject jsonBody) {
    	System.out.println(jsonBody.get("slack_id"));
    		try {
			if (db.getDBManager().authenticate(jsonBody.get("slack_id").getAsString())) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    		return false;
    	}
    
}