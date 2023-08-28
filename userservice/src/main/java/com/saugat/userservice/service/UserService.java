package com.saugat.userservice.service;

import com.saugat.userservice.entities.User;
import com.saugat.userservice.resource.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
public interface UserService {
    ApiResponse<UserDto> userById(int id);
    ApiResponse<UserPageableResponse> allUsers(PageRequestObj pageRequest);
    UserDto save(User user);
    UserDto userByEmail(String email);
    UserDetails getUserDetail(String token);

}
