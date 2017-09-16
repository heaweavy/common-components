package com.github.heaweavy.common.components.flux.server.handler;

import com.github.heaweavy.common.components.flux.server.repository.UserRepository;
import com.github.heaweavy.common.components.shared.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class UserHandler {

	@Autowired
	private UserRepository userRepository;

	public Mono<ServerResponse> handleGetUsers(ServerRequest request) {
		return ServerResponse.ok().body(userRepository.getUsers(), User.class);
	}

	public Mono<ServerResponse> handleGetUserById(ServerRequest request) {
		return userRepository.getUserById(request.pathVariable("id"))
				.flatMap(user -> ServerResponse.ok().body(Mono.just(user), User.class))
				.switchIfEmpty(ServerResponse.notFound().build());
	}

}
