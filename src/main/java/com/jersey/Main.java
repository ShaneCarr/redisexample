package com.jersey;

import com.fasterxml.jackson.core.util.JacksonFeature;
import com.jersey.resources.HelloWorldResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
//import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;


public class Main {

    private static final int DEFAULT_PORT = 8080;

    private final int serverPort;

    public Main(int serverPort) throws Exception {
        this.serverPort = serverPort;


        Server server = configureServer();
        server.start();
        server.join();
    }

    private Server configureServer() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.packages(HelloWorldResource.class.getPackage().getName());
        resourceConfig.register(new HelloWorldResource("test", "shane"));
        resourceConfig.register(JacksonFeature.class);
        ServletContainer servletContainer = new ServletContainer(resourceConfig);
        ServletHolder sh = new ServletHolder(servletContainer);
        Server server = new Server(serverPort);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.addServlet(sh, "/*");
        server.setHandler(context);
        return server;
    }

    public static void main(String[] args) throws Exception {

        int serverPort = DEFAULT_PORT;

        if(args.length >= 1) {
            try {
                serverPort = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new Main(serverPort);
    }

}


/*
package com.jersey;

import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import sun.jvm.hotspot.HelloWorld;
//import org.glassfish.jersey.jetty.JettyHttpContainerFactory;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static void main(String[] args) {

        Server server = new Server(8080);

        */
/* should be educational to look at how Jersery configures jetty *//*

        //final ResourceConfig config = new ResourceConfig(MyResource.class);
       // final Server server =
       //         JettyHttpContainerFactory.createServer(URI.create(BASE_URI), config);

        ServletContextHandler ctx =
                new ServletContextHandler(ServletContextHandler.SESSIONS);

        //With the setContextPath() method we set the path to which the application is mapped.
        ctx.setContextPath("/");
        server.setHandler(ctx);

        //DefaultServlet defaultServlet = new DefaultServlet();
        //ServletHolder defaultHolder = new ServletHolder("default", defaultServlet);
       // holderPwd.setInitParameter("resourceBase", "./src/webapp/");

        //ctx.addServlet(defaultHolder, "/*");//LINE N

        //  ServletContainer extends HttpServlet implements Filter, Container
        // add the Jersey ServletContainer to the Jetty servlet holder. This essentially joins Jersey with Jetty.
        ServletHolder serHol = ctx.addServlet(ServletContainer.class, "/*");
        serHol.setInitOrder(1);

        // tell Jersey where to look for resources.
        serHol.setInitParameter("jersey.config.server.provider.packages",
               "com.jersey.resources" );
      // final ResourceConfig config = new ResourceConfig().packages("com.mkyong");

        // Start Jetty Server
*/
/*        final Server server =
                JettyHttpContainerFactory.createServer(
                        URI.create("http://localhost:8080/"), config);*//*


        try {
            server.start();
            server.join();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
          //  server.destroy();
        }
    }
}*/
