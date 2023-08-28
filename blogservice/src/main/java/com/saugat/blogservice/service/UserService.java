package com.saugat.blogservice.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
public interface UserService {
    UserDetails getUserDetailsFromToken(String token);
}
