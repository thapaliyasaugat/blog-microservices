package com.saugat.userservice.service.serviceimpl;

import com.saugat.userservice.configuration.CustomUserDetailService;
import com.saugat.userservice.configuration.JwtConfiguration;
import com.saugat.userservice.entities.User;
import com.saugat.userservice.exceptions.ResourceNotFoundException;
import com.saugat.userservice.repositories.UserRepo;
import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.PageRequestObj;
import com.saugat.userservice.resource.UserDto;
import com.saugat.userservice.resource.UserPageableResponse;
import com.saugat.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final ModelMapper modelMapper;

    private final JwtConfiguration jwtConfiguration;
    private final CustomUserDetailService userDetailsService;

    @Override
    public ApiResponse<UserDto> userById(int id) {
        User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user","id",String.valueOf(id)));
        return new ApiResponse<>(modelMapper.map(user,UserDto.class),"user with id ".concat(String.valueOf(id)),0);
    }

    @Override
    public ApiResponse<UserPageableResponse> allUsers(PageRequestObj pageRequest) {
        Pageable p = org.springframework.data.domain.PageRequest.of(pageRequest.getPageNumber(),pageRequest.getPageSize());
        Page<User> users = userRepo.findAll(p);
        List<UserDto> userDtos = users.getContent().stream().map(u->modelMapper.map(u, UserDto.class)).collect(Collectors.toList());
        UserPageableResponse response = new UserPageableResponse();
        response.setContent(userDtos);
        response.setPageNumber(users.getNumber());
        response.setPageSize(users.getSize());
        response.setTotalNoOfPages(users.getTotalPages());
        response.setTotalNoOfElements(users.getTotalElements());
        return new ApiResponse<>(response,"pageable response of user data.",0);
    }

    @Override
    public UserDto save(User user) {
        return modelMapper.map(userRepo.save(user),UserDto.class);
    }

    @Override
    public UserDto userByEmail(String email) {
        try {
            return modelMapper.map(userRepo.findByEmail(email), UserDto.class);
        }catch (Exception e){
            throw new ResourceNotFoundException("user","email",email);
        }
    }

    @Override
    public UserDetails getUserDetail(String token) {
        String email = jwtConfiguration.getEmailFromToken(token);
        return userDetailsService.loadUserByUsername(email);
    }
}
