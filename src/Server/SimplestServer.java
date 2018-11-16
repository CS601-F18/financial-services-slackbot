package Server;

/**
 * The simplest possible Jetty server.
 */
public class SimplestServer {

	public static void main(String[] args){
	    JettyServer jettyServer = new JettyServer();
	    try {
			jettyServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}