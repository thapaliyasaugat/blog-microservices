package com.saugat.blogservice.resource;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Getter
@Setter
public class PostDto {
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private Date addedDate;
    private String imageName;
    private CategoryDto category;
    private String user;
}
