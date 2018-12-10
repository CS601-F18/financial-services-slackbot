package cs601.sideproject.server;

import cs601.sideproject.database.Database;

/**
 * Start Jetty Server and Create instance of Database
 * 
 * @author nkebbas
 */
public class StartServer {

	public static void main(String[] args){
	    FinancialServicesBotServer jettyServer = new FinancialServicesBotServer();
	    try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    /* Create a connection to the database. */
	    Database db = Database.getInstance();
	    db.connect();
	}
}