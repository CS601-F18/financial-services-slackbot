package Server;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import cs601.project4.servlets.BlockingServlet;
import cs601.project4.slackbot.EventHandler;
import cs601.project4.slackbot.HomeHandler;
import cs601.project4.slackbot.OauthConfirm;
import cs601.project4.slackbot.OauthConfirmStep2;
import cs601.project4.slackbot.SignInHandler;
import cs601.project4.slackbot.Slackbot;
import cs601.project4.slackbot.TransactionHandler;

/* https://25badcb0.ngrok.io/signin */
public class JettyServer {
    private Server server;
 
    public void start() throws Exception {
        int maxThreads = 10;
        int minThreads = 1;
        int idleTimeout = 120;
        
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(5000);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(BlockingServlet.class, "/status");
        servletHandler.addServletWithMapping(Slackbot.class, "/slackbot");
        servletHandler.addServletWithMapping(TransactionHandler.class, "/transaction");
        servletHandler.addServletWithMapping(OauthConfirm.class, "/auth");
        servletHandler.addServletWithMapping(OauthConfirmStep2.class, "/auth/confirm");
        servletHandler.addServletWithMapping(EventHandler.class, "/event");
        servletHandler.addServletWithMapping(SignInHandler.class, "/signin");
        servletHandler.addServletWithMapping(HomeHandler.class, "/auth/confirm/home");
        
        server.start();
    }
}
