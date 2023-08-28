package com.saugat.userservice.controller;

import com.saugat.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/detail")
    public UserDetails getUserDetail(@RequestParam("token") String token) {
        return userService.getUserDetail(token);
    }
}
