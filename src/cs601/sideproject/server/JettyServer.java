package cs601.sideproject.server;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import cs601.sideproject.application.GetPricesHandler;
import cs601.sideproject.application.GetStocksHandler;
import cs601.sideproject.application.HomeHandler;
import cs601.sideproject.application.OauthConfirm;
import cs601.sideproject.application.OauthConfirmStep2;
import cs601.sideproject.application.RealTimeMessaging;
import cs601.sideproject.application.SignInHandler;
import cs601.sideproject.application.Slackbot;
import cs601.sideproject.application.TransactionHandler;

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

        servletHandler.addServletWithMapping(Slackbot.class, "/slackbot");
        servletHandler.addServletWithMapping(TransactionHandler.class, "/transaction");
        servletHandler.addServletWithMapping(GetPricesHandler.class, "/prices");
        servletHandler.addServletWithMapping(OauthConfirm.class, "/auth");
        servletHandler.addServletWithMapping(OauthConfirmStep2.class, "/auth/confirm");
        servletHandler.addServletWithMapping(RealTimeMessaging.class, "/event");
        servletHandler.addServletWithMapping(GetStocksHandler.class, "/stocks");
        servletHandler.addServletWithMapping(SignInHandler.class, "/signin");
        servletHandler.addServletWithMapping(HomeHandler.class, "/auth/confirm/home");
        server.start();
    }
}
