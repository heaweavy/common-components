package com.github.heaweavy.common.components.flux.server.routes;

import com.github.heaweavy.common.components.flux.server.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {
	
	private UserHandler userHandler;

	public Routes(UserHandler userHandler) {
		this.userHandler = userHandler;
	}
	
	@Bean
	public RouterFunction<?> routerFunction() {
		return
				route(GET("/api/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::handleGetUsers)
				.and(route(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::handleGetUserById))
				.and(route(POST("/users"), userHandler::handleGetUsers));
		
	}

}
