package com.github.heaweavy.common.components.webserver2;


import ch.qos.logback.classic.Logger;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import io.undertow.servlet.api.ListenerInfo;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Created by Rogers on 15/10/12.
 */
public class HttpServer {
    public void start(String bindingHost, int bindingPort) throws ServletException{
        DeploymentInfo servletBuilder =
                Servlets.deployment().setDeploymentName("rest-server.war").setClassLoader(HttpServer.class.getClassLoader()).setContextPath("/api")
                        .addInitParameter("resteasy.guice.modules", MainModule.class.getName())
                        .addInitParameter("resteasy.logger.type", "SLF4J")
                        .addListener(new ListenerInfo(GuiceResteasyBootstrapServletContextListener.class))
                        .addServlets(Servlets.servlet("Resteasy", HttpServletDispatcher.class).addMapping("/*"));

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();

        HttpHandler servletDeploymentHandler = manager.start();

        PathHandler pathHandler = Handlers.path(Handlers.redirect("/api")).addPrefixPath("/api", servletDeploymentHandler);

        HttpHandler httpHandler = pathHandler;
        //此处根据是否开启root logger debug级别来决定是否dump Request response header数据
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        if(root.isDebugEnabled()) {
            httpHandler = Handlers.requestDump(pathHandler);
        }

        final Undertow server = Undertow.builder()
                .setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                .addHttpListener(bindingPort, bindingHost)
                .setHandler(httpHandler).build();

        // Arrange to stop the server at shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.out.println("Stopping Undertow server.");
                server.stop();
                Thread.currentThread().interrupt();
            }
        });

        server.start();

    }
}
