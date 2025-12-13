package com.mkr.reactive.usersblog.service;

import com.mkr.reactive.usersblog.presentation.model.UserRequest;
import com.mkr.reactive.usersblog.presentation.model.UserRest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<UserRest> createUser(Mono<UserRequest> createUserRequest);
    Mono<UserRest> getUserById(UUID id);
    Flux<UserRest> findAllUsers(int page, int limit);
}
