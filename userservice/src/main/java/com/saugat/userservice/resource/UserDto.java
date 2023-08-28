package com.saugat.userservice.resource;

import lombok.*;

import java.io.Serializable;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Serializable {
    private int id;
    private String userName;
    private String email;
}
