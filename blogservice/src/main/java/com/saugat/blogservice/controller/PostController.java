package com.saugat.blogservice.controller;

import com.saugat.blogservice.config.AppConstants;
import com.saugat.blogservice.resource.ApiResponse;
import com.saugat.blogservice.resource.PostDto;
import com.saugat.blogservice.resource.PostResponse;
import com.saugat.blogservice.service.FileService;
import com.saugat.blogservice.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    private final FileService fileService;

    @Value("${project.image}")
    private String path;

    @PostMapping("/user/category/{categoryId}/posts")
    public ResponseEntity createPost(@Valid @RequestBody PostDto postDto , @PathVariable("categoryId") Integer categoryId){
        PostDto post = postService.createPost(postDto,categoryId);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }
    @PutMapping("/post/{postId}/category/{categoryId}/posts")
    public ResponseEntity updatePost(@Valid @RequestBody PostDto postDto,@PathVariable("postId") Integer postId , @PathVariable("categoryId") Integer categoryId){
        PostDto post = postService.updatePost(postDto,postId,categoryId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @GetMapping("/posts/{postId}")
    public ResponseEntity getpostbyId(@PathVariable("postId") Integer postId){
        PostDto post = postService.getPostById(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @GetMapping("/posts")
    public ResponseEntity gelAllPosts(
            @RequestParam(value = "pageNumber",defaultValue= AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(value = "sortBy" ,defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir" ,defaultValue = "asc",required = false) String sortDir
    ){
        PostResponse posts = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity gePostByUser(@PathVariable("userId") Integer userid,
                                       @RequestParam(value = "pageNumber" ,defaultValue = "0",required = false) Integer pageNumber,
                                       @RequestParam(value = "pageSize" ,defaultValue = "10",required = false) Integer pageSize
    ){
        PostResponse posts= postService.getPostByUserId(userid,pageNumber,pageSize);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    } @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity gePostByCategory(@PathVariable("categoryId") Integer categoryId,
                                           @RequestParam(value = "pageNumber" ,defaultValue = "0",required = false) Integer pageNumber,
                                           @RequestParam(value = "pageSize" ,defaultValue = "10",required = false) Integer pageSize
    ){
        PostResponse posts= postService.getPostByCategory(categoryId,pageNumber,pageSize);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity deletePostById(@PathVariable("postId") Integer postId){
        ApiResponse responce = postService.deletePost(postId);
        return new ResponseEntity<>(responce,HttpStatus.OK);
    }
    @GetMapping("/posts/contains/{keyword}")
    public ResponseEntity searchPostByKeyword(@PathVariable("keyword") String keyword){
        List<PostDto> posts = postService.searchPost(keyword);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    //post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable("postId") Integer postId
    ) throws IOException {
        PostDto postDto = this.postService.getPostById(postId);
        String fileName =  this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);

        PostDto updatedPost = this.postService.updatePost(postDto,postId,postDto.getCategory().getCategoryId());
        return new ResponseEntity<>(updatedPost,HttpStatus.OK);
    }
    //serve image
    @GetMapping(value = "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              HttpServletResponse response
    ) throws IOException {
        InputStream resource = this.fileService.getResource(path,imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }
}
