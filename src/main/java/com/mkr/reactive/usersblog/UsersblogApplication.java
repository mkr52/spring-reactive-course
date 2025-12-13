package com.mkr.reactive.usersblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class UsersblogApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsersblogApplication.class, args);
    }

}
