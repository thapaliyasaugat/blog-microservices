package com.saugat.userservice.service.serviceimpl;

import com.saugat.userservice.entities.Role;
import com.saugat.userservice.repositories.RoleRepo;
import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.RoleDto;
import com.saugat.userservice.resource.RoleRequest;
import com.saugat.userservice.resource.UserDto;
import com.saugat.userservice.service.RoleService;
import com.saugat.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepo roleRepo, UserService userService, ModelMapper modelMapper) {
        this.roleRepo = roleRepo;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<List<RoleDto>> roleOfUser(int id) {
        List<Role> roles = roleRepo.roleOfUser(id);
        return new ApiResponse<>(
                roles.stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList()),
                "roles of user ".concat(String.valueOf(id)),
                0
        );
    }

    @Override
    public ApiResponse<List<RoleDto>> allRoles() {
        List<Role> roles = roleRepo.findAll();
        List<RoleDto> roleDtoList = roles.stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList());
        return new ApiResponse<>(roleDtoList, "all Roles", 0);
    }

    @Override
    public RoleDto findByName(String name) {
        try {
            Role role = roleRepo.findByName(name);
            return modelMapper.map(role, RoleDto.class);
        } catch (Exception e) {
            log.error("error finding role by name {}", e.getMessage());
            throw new RuntimeException("No such Roles Defined.");
        }
    }

    @Override
    public ApiResponse<List<RoleDto>> getUserRoles(String email) {
        UserDto user = userService.userByEmail(email);
        List<Role> roles = roleRepo.roleOfUser(user.getId());
        return new ApiResponse<>(
                roles.stream().map(role -> modelMapper.map(role, RoleDto.class)).collect(Collectors.toList()),
                "roles of user ".concat(user.getUserName()),
                0
        );
    }

    @Override
    public ApiResponse<RoleDto> createRole(RoleRequest roleRequest) {
        try {
            log.info("Request to create role : {}", roleRequest);
            Role role = roleRepo.save(modelMapper.map(roleRequest, Role.class));
            return new ApiResponse<>(modelMapper.map(role, RoleDto.class), "Role Created Successfully.", 0);
        } catch (Exception e) {
            log.info("Exception on role creation : {}", e.getMessage());
            throw new RuntimeException("Error creating role. Check if roleName already exists.");
        }
    }
}

