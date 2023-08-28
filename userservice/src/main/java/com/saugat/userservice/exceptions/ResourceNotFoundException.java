package com.saugat.userservice.exceptions;

import lombok.Getter;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;
    private final String fieldName;
    private final String filedValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String filedValue) {
        super(String.format("%s not found with %s : %s",resourceName,fieldName,filedValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }
}