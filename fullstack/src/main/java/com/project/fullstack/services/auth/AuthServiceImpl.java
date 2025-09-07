package com.project.fullstack.services.auth;

import com.project.fullstack.entities.User;
import com.project.fullstack.enums.UserRole;
import com.project.fullstack.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    @PostConstruct
    public void createAnAdminAccount (){
        Optional<User> optionalUser = userRepository.findFirstByEmail(String.valueOf(UserRole.ADMIN));
        if(optionalUser.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created susccessfully");

        } else {
            // Admin account already exists
            System.out.println("Admin account already exists");
        }
    }
}
