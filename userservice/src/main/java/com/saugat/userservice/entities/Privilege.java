package com.saugat.userservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * Created by Saugat Thapaliya on 8/27/2023
 **/
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}

