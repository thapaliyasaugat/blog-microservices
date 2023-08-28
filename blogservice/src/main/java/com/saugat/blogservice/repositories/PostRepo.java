package com.saugat.blogservice.repositories;

import com.saugat.blogservice.entities.Category;
import com.saugat.blogservice.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
public interface PostRepo extends JpaRepository<Post,Integer> {
    @Query("select p from Post p where userId=:user")
    Page<Post> findByUser(String user, Pageable pageable);

    Page<Post> findByCategory(Category category, Pageable pageable);

    List<Post> findByTitleContaining(String keyword);

}
