package com.project.fullstack.dto;

import com.project.fullstack.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }}
