package com.saugat.userservice.resource;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {
    @NotEmpty(message = "UserName can't be empty")
    private String userName;

    @Email(message = "email is not valid.")
    @NotEmpty(message = "email can't be empty.")
    private String email;

    @NotEmpty(message = "password can't be empty")
    @Size(min = 6,message = "password must be greater than 6 character.")
    private String password;
}
