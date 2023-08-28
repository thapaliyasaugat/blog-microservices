package com.saugat.userservice.resource;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotEmpty(message = "email can't be empty")
    private String email;
    @NotEmpty(message = "password can't be empty")
    private String password;
}
