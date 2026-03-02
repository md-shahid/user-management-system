package com.userManagementSystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Employee {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
