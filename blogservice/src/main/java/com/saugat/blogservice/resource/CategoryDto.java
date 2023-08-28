package com.saugat.blogservice.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(max = 100,message = "category title can't be empty")
    private String categoryTitle;

    private String categoryDescription;
}
