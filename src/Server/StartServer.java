package Server;

import cs601.database.Database;

/**
 * The simplest possible Jetty server.
 */
public class StartServer {

	public static void main(String[] args){
	    JettyServer jettyServer = new JettyServer();
	    try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	    System.out.println("server started");
	    /* Create a connection to the database. 
	     * Could also move this into separate (singleton) class and 
	     * perform this logic there.  */
	    Database db = Database.getInstance();
	    db.connect();
	}
}