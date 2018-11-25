package cs601.sideproject.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
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

/**
 * Makes API calls to https://api.iextrading.com/1.0/stock/fb/book to 
 * get Current stock prices.
 */
public class GetStocksHandler extends HttpServlet{
	private Map<String, String> arguments;
	
	public GetStocksHandler() {
		arguments = new HashMap<String, String>();
	}
	
	protected void doPost( HttpServletRequest request, 
    		HttpServletResponse response)
      throws ServletException, IOException {
		String stock = "";
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
        		arguments.put(splitArguments[0], splitArguments[1]);	
        }
        stock = arguments.get("text");
        
        /* Then make an API Call */
        response.getWriter().println("You said: " + arguments.get("text"));
        System.out.println("stock " + stock);
	   	URL url = new URL("https://api.iextrading.com/1.0/stock/"+ stock + "/book");
        HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
        connect.connect();
        if (connect.getResponseCode() == 200) {
	        BufferedReader br = new BufferedReader(new InputStreamReader((connect.getInputStream())));
	        StringBuilder sb = new StringBuilder();
	        String output;
	        while ((output = br.readLine()) != null) {
	          sb.append(output);
	        }
	        JsonParser jp = new JsonParser();
	        JsonObject jsonTree = jp.parse(sb.toString()).getAsJsonObject();
	        JsonObject bpi = jsonTree.get("quote").getAsJsonObject();
	        String companyName = bpi.get("companyName").getAsString();
	        String sharePrice = bpi.get("latestPrice").getAsString();
	        response.getWriter().write("The price of " + companyName + " "  + "is currently " + sharePrice +  ".");
        } else {
	        BufferedReader br = new BufferedReader(new InputStreamReader((connect.getErrorStream())));
	        StringBuilder sb = new StringBuilder();
	        String output;
	        while ((output = br.readLine()) != null) {
	          sb.append(output);
	        }
	        System.out.println (sb.toString());
        }
   }
}
