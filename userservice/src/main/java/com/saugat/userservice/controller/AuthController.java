package com.saugat.userservice.controller;

import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.JwtAuthResponse;
import com.saugat.userservice.resource.LoginRequest;
import com.saugat.userservice.resource.SignUpRequest;
import com.saugat.userservice.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/signup")
    public ApiResponse<?> signUpUser(@Valid @RequestBody SignUpRequest signUpRequest){
        return authService.signUpUser(signUpRequest);
    }

    @PostMapping("/signin")
    public ApiResponse<JwtAuthResponse> signInUser(@Valid @RequestBody LoginRequest loginRequest){
        System.out.println("check.");
//        ApiResponse<JwtAuthResponse> response = authService.signInUser(loginRequest);
        return authService.signInUser(loginRequest);
    }
}