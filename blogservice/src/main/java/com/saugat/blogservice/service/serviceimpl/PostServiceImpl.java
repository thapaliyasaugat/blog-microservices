package com.saugat.blogservice.service.serviceimpl;

import com.saugat.blogservice.entities.Category;
import com.saugat.blogservice.entities.Post;
import com.saugat.blogservice.exceptions.ResourceNotFoundException;
import com.saugat.blogservice.repositories.CategoryRepo;
import com.saugat.blogservice.repositories.PostRepo;
import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.PostDto;
import com.saugat.blogservice.resource.PostResponse;
import com.saugat.blogservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    @Override
    public PostDto createPost(PostDto postDto, Integer categoryId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","id",categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUserId(username);
        post.setCategory(category);
        Post savedPost = postRepo.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer id, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCategory(category);
        post.setImageName(postDto.getImageName());
        Post updatedPost = postRepo.save(post);
        return modelMapper.map(updatedPost,PostDto.class);
    }

    @Override
    public PostDto getPostById(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        return modelMapper.map(post,PostDto.class);
    }

    @Override
    public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }else{
            sort = Sort.by(sortBy).descending();
        }
        Pageable p= PageRequest.of(pageNumber,pageSize,sort);
        Page<Post> pagePosts = postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> returnPosts = posts.stream().map((post)->modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(returnPosts);
        postResponse.setPageNumber(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElememts(pagePosts.getTotalElements());
        postResponse.setLastPage(pagePosts.isLast());
        return postResponse;
    }

    @Override
    public ApiResponse deletePost(Integer id) {
        Post post = postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        postRepo.deleteById(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("post deleted sucessfully.");
        response.setSuccess(true);
        return response;
    }

    @Override
    public PostResponse getPostByCategory(Integer categoryId,Integer pageNumber,Integer pageSize) {

        Pageable p=PageRequest.of(pageNumber,pageSize);
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        Page<Post> pagePosts = postRepo.findByCategory(category,p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> returnposts = posts.stream().map((post) ->(
                modelMapper.map(post,PostDto.class)
        )).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(returnposts);;
        postResponse.setTotalElememts(pagePosts.getTotalElements());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setPageNumber(pagePosts.getNumber());
        return postResponse;
    }

    @Override
    public PostResponse getPostByUserId(Integer userId,Integer pageNumber , Integer pageSize) {
        Pageable p = PageRequest.of(pageNumber,pageSize);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Page<Post> pagePosts = postRepo.findByUser(username,p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> returnposts = posts.stream().map((post) ->(
                modelMapper.map(post,PostDto.class)
        )).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(returnposts);;
        postResponse.setTotalElememts(pagePosts.getTotalElements());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setPageNumber(pagePosts.getNumber());
        return postResponse;
    }

    @Override
    public List<PostDto> searchPost(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
//        List<Post> posts = postRepo.findByTitleContaining("%"+keyword+"%");
        List<PostDto> returnPosts = posts.stream().map((post->(modelMapper.map(post,PostDto.class)))).collect(Collectors.toList());
        return returnPosts;
    }
}
