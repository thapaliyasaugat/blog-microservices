package com.saugat.blogservice.config;

import com.saugat.blogservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> {
                    request.requestMatchers("/api/blogs/**").hasAuthority("MANAGE_USER")
                            .requestMatchers(HttpMethod.POST, "/api/admin/**").hasAnyAuthority("MANAGE_CATEGORY")
                            .anyRequest().authenticated()
                            ;
                }).addFilterAfter(getBlogFilter(), BasicAuthenticationFilter.class);
        return http.build();
    }

    private BlogFilter getBlogFilter(){
        return new BlogFilter(userService);
    }
}
