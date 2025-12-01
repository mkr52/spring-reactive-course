package com.mkr.reactive.usersblog.service;

import com.mkr.reactive.usersblog.data.UserEntity;
import com.mkr.reactive.usersblog.data.UserRepository;
import com.mkr.reactive.usersblog.presentation.UserRequest;
import com.mkr.reactive.usersblog.presentation.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
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
                .map(this::convertToEntity)
                .flatMap(userRepository::save) // removes the nested Mono<UserEntity> to Mono<UserEntity>
                .map(this::convertToRest);
    }

    @Override
    public Mono<UserRest> getUserById(UUID id) {
        return userRepository.findById(id)
                .mapNotNull(this::convertToRest);
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
