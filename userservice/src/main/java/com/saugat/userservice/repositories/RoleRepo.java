package com.saugat.userservice.repositories;

import com.saugat.userservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findByName(String roleName);
    @Query(value = "select * from roles where id in (select role_id from user_roles where user_id=:id)",nativeQuery = true)
    List<Role> roleOfUser(int id);
}
