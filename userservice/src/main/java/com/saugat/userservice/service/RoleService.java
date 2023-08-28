package com.saugat.userservice.service;

import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.RoleDto;
import com.saugat.userservice.resource.RoleRequest;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
public interface RoleService {
    ApiResponse<List<RoleDto>> roleOfUser(int id);
    ApiResponse<List<RoleDto>> allRoles();
    RoleDto findByName(String name);
    ApiResponse<List<RoleDto>> getUserRoles(String email);
    ApiResponse<RoleDto> createRole(RoleRequest roleRequest);

}
