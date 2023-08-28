package com.saugat.userservice.repositories;

import com.saugat.userservice.entities.Role;
import com.saugat.userservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    @Query(value = "select * from user_roles where user_id=:id",nativeQuery = true)
    List<Role> getUserRoles(int id);
}
