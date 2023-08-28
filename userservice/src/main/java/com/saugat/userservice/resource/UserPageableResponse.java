package com.saugat.userservice.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPageableResponse {
    private List<UserDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalNoOfElements;
    private int totalNoOfPages;
}

