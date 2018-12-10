package cs601.sideproject.server;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import cs601.sideproject.application.Constants;
import cs601.sideproject.application.HomeHandler;
import cs601.sideproject.application.RealTimeMessaging;
import cs601.sideproject.application.SignInHandler;
import cs601.sideproject.application.Slackbot;
import cs601.sideproject.application.cryptocurrency.GetPricesHandler;
import cs601.sideproject.application.stock.GetStocksHandler;
import cs601.sideproject.application.stock.RecordStockHandler;
import cs601.sideproject.application.stock.StockSuggestionsHandler;
import cs601.sideproject.application.transaction.TransactionHandler;
import cs601.sideproject.authentication.OauthConfirm;
import cs601.sideproject.authentication.Dashboard;

/**
 * Handles routing to handlers
 * author: nkebbas
 * */
public class FinancialServicesBotServer {
    private Server server;
 
    public void start() throws Exception {
        int maxThreads = 10;
        int minThreads = 1;
        int idleTimeout = 120;
        
        QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
        server = new Server(threadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(Constants.PORT);
        server.setConnectors(new Connector[] { connector });

        ServletHandler servletHandler = new ServletHandler();
        server.setHandler(servletHandler);

        servletHandler.addServletWithMapping(HomeHandler.class, "/");
        servletHandler.addServletWithMapping(Slackbot.class, "/slackbot");
        servletHandler.addServletWithMapping(Slackbot.class, "/authorize");
        servletHandler.addServletWithMapping(TransactionHandler.class, "/transaction");
        servletHandler.addServletWithMapping(RecordStockHandler.class, "/stocktransaction");
        servletHandler.addServletWithMapping(GetPricesHandler.class, "/prices");
        servletHandler.addServletWithMapping(OauthConfirm.class, "/auth");
        servletHandler.addServletWithMapping(Dashboard.class, "/auth/confirm");
        servletHandler.addServletWithMapping(Dashboard.class, "/dashboard");
        servletHandler.addServletWithMapping(RealTimeMessaging.class, "/event");
        servletHandler.addServletWithMapping(GetStocksHandler.class, "/stocks");
        servletHandler.addServletWithMapping(StockSuggestionsHandler.class, "/stocks/suggestions");
        servletHandler.addServletWithMapping(SignInHandler.class, "/signin");
        servletHandler.addServletWithMapping(HomeHandler.class, "/auth/confirm/home");
        server.start();
    }
}
