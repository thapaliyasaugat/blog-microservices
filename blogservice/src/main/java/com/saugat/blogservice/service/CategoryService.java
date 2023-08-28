package com.saugat.blogservice.service;

import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.CategoryDto;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
    ApiResponse deleteCategory(Integer categoryId);
    CategoryDto getCategory(Integer categoryId);
    List<CategoryDto> getListOfAllCategory();
}
