package com.mkr.reactive.usersblog.exception;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public Mono<ErrorResponse> handleDuplicateKeyException(DuplicateKeyException exception) {
        // Implementation for handling duplicate key exceptions
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.CONFLICT, exception.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGenericException(Exception exception) {
        // Implementation for handling duplicate key exceptions
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage())
                .build());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException exception) {
        String errorMessage = exception.getBindingResult().getAllErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));
//                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
//                .orElse("Validation error");
        // Implementation for handling duplicate key exceptions
        return Mono.just(ErrorResponse.builder(exception, HttpStatus.BAD_REQUEST, errorMessage)
                .build());
    }
}
