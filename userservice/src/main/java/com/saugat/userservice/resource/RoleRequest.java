package com.saugat.userservice.resource;

import com.saugat.userservice.entities.Privilege;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RoleRequest {
    @NotEmpty(message = "roleName can't be empty.")
    private String name;
    @NotNull(message = "Privileges can't be null.")
    private Set<Privilege> privileges;
}