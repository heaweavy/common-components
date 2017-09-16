package com.github.heaweavy.common.components.flux.server.repository;

import com.github.heaweavy.common.components.shared.entity.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Repository
public class UserRepository {

	private final List<User> users = Arrays.asList(new User(1L, "User1"), new User(2L, "User2"));

	public Mono<User> getUserById(String id) {
		return Mono.justOrEmpty(users.stream().filter(user -> {
			return user.getId().equals(Long.valueOf(id));
		}).findFirst().orElse(null));
	}

	public Flux<User> getUsers() {
		return Flux.fromIterable(users);
	}

}
