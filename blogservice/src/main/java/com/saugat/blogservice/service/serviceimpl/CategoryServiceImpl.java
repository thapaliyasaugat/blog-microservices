package com.saugat.blogservice.service.serviceimpl;

import com.saugat.blogservice.entities.Category;
import com.saugat.blogservice.exceptions.ResourceNotFoundException;
import com.saugat.blogservice.repositories.CategoryRepo;
import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.CategoryDto;
import com.saugat.blogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryRepo.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category updatedCategory = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        updatedCategory.setCategoryTitle(categoryDto.getCategoryTitle());
        updatedCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category returnValue = categoryRepo.save(updatedCategory);
        return modelMapper.map(returnValue,CategoryDto.class);
    }

    @Override
    public ApiResponse deleteCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        categoryRepo.deleteById(categoryId);
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setMessage("Category Deleted Sucessfully");
        return apiResponse;
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getListOfAllCategory() {
        List<Category> listOfCategories = categoryRepo.findAll();
        List<CategoryDto> finalList = listOfCategories.stream().map(category->modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
        return finalList;
    }
}
