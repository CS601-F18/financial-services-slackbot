package cs601.sideproject.application.cryptocurrency;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
/**
 * 
 * Make API calls to https://api.coindesk.com/v1/bpi/currentprice.json to
 * get current price of cryptocurrencies.
 * @author nkebbas
 */
public class GetPricesHandler extends HttpServlet {
	   protected void doPost( HttpServletRequest request, 
	    		HttpServletResponse response)
	      throws ServletException, IOException {
		   	URL url = new URL("https://api.coindesk.com/v1/bpi/currentprice.json");
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
		        JsonObject bpi = jsonTree.get("bpi").getAsJsonObject();
		        String chartName = jsonTree.get("chartName").getAsString();
		        String rate = bpi.get("USD").getAsJsonObject().get("rate").getAsString();
		        response.getWriter().write("The price of " + chartName + " "  + "is currently " + rate +  ".");
	        } else {
		        BufferedReader br = new BufferedReader(new InputStreamReader((connect.getErrorStream())));
		        StringBuilder sb = new StringBuilder();
		        String output;
		        while ((output = br.readLine()) != null) {
		          sb.append(output);
		        }
	        }
	   }
}
