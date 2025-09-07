package com.project.fullstack.dto;

import com.project.fullstack.enums.UserRole;
import com.project.fullstack.services.jwt.UserService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;

@Data
public class AuthentificationResponse {

    private String jwt;
    private Long userId;
    private UserRole userRole;

    public void authenticate(AuthenticationManager authenticationManager, UserService userService) {
    }
}
