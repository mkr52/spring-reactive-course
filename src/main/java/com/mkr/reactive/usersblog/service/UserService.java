package com.mkr.reactive.usersblog.service;

import com.mkr.reactive.usersblog.presentation.UserRequest;
import com.mkr.reactive.usersblog.presentation.UserRest;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserService {
    Mono<UserRest> createUser(Mono<UserRequest> createUserRequest);
    Mono<UserRest> getUserById(UUID id);
}
