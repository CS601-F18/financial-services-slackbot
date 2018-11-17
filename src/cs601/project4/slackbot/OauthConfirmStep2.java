package cs601.project4.slackbot;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.*;

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
        InputStream in = connect.getInputStream();
        String encoding = connect.getContentEncoding();
        String body = IOUtils.toString(in, encoding);
        System.out.println(body);
        
        /* Now get all that JSON Data because we need to save it */
        /* TODO: Make this into a helper method because I keep using it */
//        String getBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//        System.out.println(getBody);
//        JsonParser parser = new JsonParser();
//        JsonObject jsonBody = (JsonObject) parser.parse(getBody);
//        System.out.println(jsonBody);
        
       // response.sendRedirect("https://slack.com/api/oauth.access?code=" + request.getParameter("code") + "&client_id=" +Constants.CLIENT_ID + "&client_secret=" + Constants.CLIENT_SECRET + "&redirect_uri=" + Constants.REDIRECT);
       System.out.println("oauth confirm step 2 handler hit");
    }
    
    protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"ok\"}");
        System.out.println("post request");
        System.out.println(request.getContentLength());
        System.out.println(request.getQueryString());
        System.out.println(request.getParameter("Token"));
    }
}