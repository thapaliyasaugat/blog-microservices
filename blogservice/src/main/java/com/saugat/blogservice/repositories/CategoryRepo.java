package com.saugat.blogservice.repositories;

import com.saugat.blogservice.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Saugat Thapaliya on 8/28/2023
 **/
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}