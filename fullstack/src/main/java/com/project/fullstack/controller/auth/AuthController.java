package com.project.fullstack.controller.auth;

import com.project.fullstack.dto.AuthentificationResponse;
import com.project.fullstack.dto.SignupRequest;
import com.project.fullstack.dto.UserDto;
import com.project.fullstack.entities.User;
import com.project.fullstack.repositories.UserRepository;
import com.project.fullstack.services.auth.AuthService;
import com.project.fullstack.services.jwt.UserService;
import com.project.fullstack.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import com.project.fullstack.*;
@RestController
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists with this email");
        }
        UserDto createdUser = authService.signupUser(signupRequest);
        if (createdUser == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created. (Failed to create user)");
        return ResponseEntity.ok(authService.signupUser(signupRequest));
    }

    @PostMapping("/login")
    public AuthentificationResponse login(@RequestBody AuthentificationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        User user = userRepository.findFirstByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found after successful authentication"));
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        final String jwtToken = jwtUtil.generateToken(userDetails);

        AuthentificationResponse authenticationResponse = new AuthentificationResponse();
        authenticationResponse.setJwt(jwtToken);
        authenticationResponse.setUserId(user.getId());
        authenticationResponse.setUserRole(user.getUserRole());

        return authenticationResponse;
    }

    @GetMapping("/test")
    public String test() {
        return "Server is working!";
    }
}
