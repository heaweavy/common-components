package com.github.heaweavy.common.components.flux.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.result.view.RedirectView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;
import reactor.ipc.netty.http.server.HttpServer;

//import org.springframework.web.servlet.view.UrlBasedViewResolver;
//import org.springframework.web.servlet.view.JstlView;
//
//import com.samskivert.mustache.Template;
//import com.samskivert.mustache.Mustache.Compiler;

@Configuration
public class HttpServerConfig {

	@Autowired
	private Environment environment;

	@Bean
	public HttpServer httpServer(RouterFunction<?> routerFunction) {
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create("localhost", Integer.valueOf(environment.getProperty("server.port")));
		server.newHandler(adapter);
		return server;
	}
}
