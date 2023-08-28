package com.saugat.blogservice.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    String resourceName;
    String fieldname;
    long fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldname, int fieldValue) {
        super(String.format("%s not found with %s : %d",resourceName,fieldname,fieldValue));
        this.resourceName = resourceName;
        this.fieldname = fieldname;
        this.fieldValue = fieldValue;
    }
}
