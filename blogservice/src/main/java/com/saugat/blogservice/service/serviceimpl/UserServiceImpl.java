package com.saugat.blogservice.service.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saugat.blogservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${user.url:http://localhost:8100}")
    private String userUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public UserDetails getUserDetailsFromToken(String token) {
        //call user
        try {
            String url = userUrl + "/api/user/detail?token=" + token;
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(null), Object.class);
            return objectMapper.convertValue(response.getBody(), UserDetails.class);
        } catch (Exception e) {
            log.error("error calling user for detail, error : {}", e.getMessage());
            throw new RuntimeException("Unauthorized Request");
        }
    }
}
