package com.project.fullstack.services.auth;

import com.project.fullstack.dto.SignupRequest;
import com.project.fullstack.dto.UserDto;

public interface AuthService {


    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
