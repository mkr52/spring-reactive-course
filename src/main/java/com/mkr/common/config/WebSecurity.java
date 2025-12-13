package com.mkr.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
//@EnableWebFluxSecurity
@EnableWebSecurity
@ComponentScan("com.mkr")
public class WebSecurity {

//    @Bean
//    SecurityWebFilterChain httpSecurityFilterChain(ServerHttpSecurity http) {
//        http
//            .authorizeExchange(exchanges -> exchanges
//                    .pathMatchers(HttpMethod.POST, "/users").permitAll()
//                    .pathMatchers(HttpMethod.GET, "/users/info").permitAll()
//                    .pathMatchers(HttpMethod.POST, "/login").permitAll()
//                .anyExchange().authenticated())
//                .csrf(csrf -> csrf.disable());
//        return http.build();
//    }

    @Bean
    SecurityFilterChain httpSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/welcome").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
