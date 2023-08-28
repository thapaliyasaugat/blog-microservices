package com.saugat.blogservice.controller;

import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.CategoryDto;
import com.saugat.blogservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    @PutMapping("/{catId}")
    public ResponseEntity updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable("catId") int id){
        CategoryDto updatedCategory = categoryService.updateCategory(categoryDto,id);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    @DeleteMapping("/{catId}")
    public ResponseEntity deleteCategory(@PathVariable("catId") int id){
        ApiResponse response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllCategory(){
        List<CategoryDto> categories = categoryService.getListOfAllCategory();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }

}