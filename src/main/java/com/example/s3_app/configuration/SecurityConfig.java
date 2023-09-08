package com.example.s3_app.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//    private final OAuth2UserService <OidcUserRequest, OidcUser> oidcUserService;
//
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(req -> req.requestMatchers("/login","/buckets/**").permitAll().anyRequest().authenticated())
//                .oauth2Login(auth -> auth.userInfoEndpoint(endPoints -> endPoints.oidcUserService(oidcUserService)))
//                .build();
//    }
//}
