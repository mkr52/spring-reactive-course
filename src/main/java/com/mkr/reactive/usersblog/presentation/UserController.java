package com.mkr.reactive.usersblog.presentation;

import com.mkr.reactive.usersblog.config.BuildInfo;
import com.mkr.reactive.usersblog.presentation.model.UserRequest;
import com.mkr.reactive.usersblog.presentation.model.UserRest;
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

    private final BuildInfo buildInfo;

    public UserController(UserService userService, BuildInfo buildInfo) {
        this.userService = userService;
        this.buildInfo = buildInfo;
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
    public Flux<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                   @RequestParam(value = "limit", defaultValue = "50") int limit) {
        return userService.findAllUsers(page, limit);
    }

    // Add controller mapping to display build info properties
    @GetMapping("/info")
    public Mono<ResponseEntity<String>> getBuildInfo() {
        System.out.println(buildInfo.getVersion());
        String result = String.format("Version: %s, Url: %s, Key: %s",
                buildInfo.getVersion(),
                buildInfo.getUrl(),
                buildInfo.getKey());
        return Mono.just(ResponseEntity.ok(result));
    }

}
