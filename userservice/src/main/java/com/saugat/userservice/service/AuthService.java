package com.saugat.userservice.service;

import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.JwtAuthResponse;
import com.saugat.userservice.resource.LoginRequest;
import com.saugat.userservice.resource.SignUpRequest;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
public interface AuthService {
    ApiResponse<JwtAuthResponse> signInUser(LoginRequest loginRequest);
    ApiResponse signUpUser(SignUpRequest signUpRequest);
}
