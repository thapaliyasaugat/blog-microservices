package com.saugat.blogservice.service;

import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.PostDto;
import com.saugat.blogservice.resource.PostResponse;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
public interface PostService {
    PostDto createPost(PostDto postDto, Integer categoryId);
    PostDto updatePost(PostDto postDto , Integer id, Integer categoryId);
    PostDto getPostById(Integer id);
    PostResponse getAllPosts(Integer pageNumber , Integer pageSize, String sortBy, String sortDir);
    ApiResponse deletePost(Integer id);
    PostResponse getPostByCategory(Integer categoryId,Integer pageNumber , Integer pageSize);
    PostResponse getPostByUserId(Integer userId,Integer pageNumber , Integer pageSize);
    List<PostDto> searchPost(String keyword);

}
