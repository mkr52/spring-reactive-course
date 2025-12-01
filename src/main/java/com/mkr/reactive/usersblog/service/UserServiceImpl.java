package com.mkr.reactive.usersblog.service;

import com.mkr.reactive.usersblog.data.UserEntity;
import com.mkr.reactive.usersblog.data.UserRepository;
import com.mkr.reactive.usersblog.presentation.UserRequest;
import com.mkr.reactive.usersblog.presentation.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserRest> createUser(Mono<UserRequest> createUserRequest) {
        return createUserRequest
                .mapNotNull(this::convertToEntity)
                .flatMap(userRepository::save) // removes the nested Mono<UserEntity> to Mono<UserEntity>
                .mapNotNull(this::convertToRest);
                // below can be replaced with a GlobalExceptionHandler
//                .onErrorMap(throwable -> {
//                            if (throwable instanceof DuplicateKeyException) {
//                                return new ResponseStatusException(HttpStatus.CONFLICT, throwable.getMessage());
//                            } else if(throwable instanceof DataIntegrityViolationException) {
//                                return new ResponseStatusException(HttpStatus.BAD_REQUEST, throwable.getMessage());
//                            }
//                            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, throwable.getMessage());
//                });
//                .onErrorMap(DuplicateKeyException.class,
//                        exception->new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage()));
    }

    @Override
    public Mono<UserRest> getUserById(UUID id) {
        return userRepository.findById(id)
                .mapNotNull(this::convertToRest);
    }

    @Override
    public Flux<UserRest> findAllUsers(int page, int limit) {
        if(page>0) page = page -1; // pages are 0 based index
        Pageable pageable = PageRequest.of(page, limit);
        return userRepository.findAllBy(pageable)
                .map(userEntity -> convertToRest(userEntity));
    }

    private UserEntity convertToEntity(UserRequest request) {
        UserEntity entity = new UserEntity();
        // Both objects must have same field names, so we can use BeanUtils
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    private UserRest convertToRest(UserEntity userEntity) {
        UserRest userRest = new UserRest();
        // Both objects must have same field names, so we can use BeanUtils
        BeanUtils.copyProperties(userEntity, userRest);
        return userRest;
    }
}
