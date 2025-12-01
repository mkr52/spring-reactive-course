package com.mkr.reactive.usersblog.presentation;

import com.mkr.reactive.usersblog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Mono<ResponseEntity<UserRest>> create(@RequestBody @Valid Mono<UserRequest> user) {
        return userService.createUser(user)
                .map((userRest -> ResponseEntity
                        .status(HttpStatus.CREATED)
                        .location(URI.create("/users/" + userRest.getId()))
                        .body(userRest)));
    }

    @GetMapping("/{userId}")
    public Mono<ResponseEntity<UserRest>> getUserById(@PathVariable("userId") UUID userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Flux<ResponseEntity<UserRest>> getUsers(@RequestParam("offset") int offset,
                                                   @RequestParam("limit") int limit) {
        return Flux.just(
                new UserRest(UUID.randomUUID(), "John", "Cena", "tes@test.com"),
                new UserRest(UUID.randomUUID(), "Jane", "Doe", "jane@do.com")
        ).map(ResponseEntity::ok);
    }
}
