package com.saugat.userservice.service.serviceimpl;

import com.saugat.userservice.configuration.JwtConfiguration;
import com.saugat.userservice.configuration.UserPrincipal;
import com.saugat.userservice.entities.Role;
import com.saugat.userservice.entities.User;
import com.saugat.userservice.resource.*;
import com.saugat.userservice.service.AuthService;
import com.saugat.userservice.service.RoleService;
import com.saugat.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Slf4j
@Service(value = "userAuthService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtConfiguration jwtConfiguration;
    private final ModelMapper modelMapper;

    @Override
    public ApiResponse<JwtAuthResponse> signInUser(LoginRequest loginRequest) {
        log.info("Login attempt from {}",loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtConfiguration.generateToken(authentication);
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
        List<RoleDto> roles = principal.getRoles().stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(jwt);
        jwtAuthResponse.setUserName(authentication.getName());
        jwtAuthResponse.setRoles(roles);
        return new ApiResponse<>(jwtAuthResponse,"Signed in successfully.",0);
    }

    @Override
    public ApiResponse<?> signUpUser(SignUpRequest signUpRequest) {
        RoleDto role = roleService.findByName("USER");
        if(role==null) throw new RuntimeException("Error Asiggning Role.");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(modelMapper.map(role,Role.class));
        User user = new User();
        user.setUserName(signUpRequest.getUserName());
        user.setEmail(signUpRequest.getEmail());
        user.setRoles(roleSet);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userService.save(user);
        ApiResponse<?> apiResponse = new ApiResponse<>();
        apiResponse.setStatus(0);
        apiResponse.setMessage("User Created successfully.");
        return apiResponse;
    }

}

