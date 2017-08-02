package Elena.Chernenkova;

import Elena.Chernenkova.Servlets.MyServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * Created by 123 on 25.07.2017.
 */
public class Launch {
    public static void main(String[] args) throws Exception {
        MyServlet testPage = new MyServlet();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(new ServletHolder(testPage), "/MyServlet");

        Server server = new Server(8080);
        HandlerList handlers = new HandlerList( );
        handlers.setHandlers( new Handler[] { context } );
        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
