package com.saugat.userservice.controller;

import com.saugat.userservice.resource.ApiResponse;
import com.saugat.userservice.resource.RoleDto;
import com.saugat.userservice.resource.RoleRequest;
import com.saugat.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@RestController
@RequestMapping("/api/admin/role")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRoles(){
        ApiResponse<List<RoleDto>> roles = roleService.allRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> roleOfUser(@PathVariable int id){
        ApiResponse<List<RoleDto>> roles = roleService.roleOfUser(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ApiResponse<?> createNewRole(@Valid @RequestBody RoleRequest roleRequest){
        return roleService.createRole(roleRequest);
    }
    @PutMapping("/update/{id}")
    public ApiResponse<?> updateRole(@RequestBody RoleRequest roleRequest,@PathVariable int id){
        return null;
    }

}
