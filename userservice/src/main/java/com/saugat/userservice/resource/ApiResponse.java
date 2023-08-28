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
public class ApiResponse<T> implements Serializable {
    private T data;
    private String message;
    private int status;
}
