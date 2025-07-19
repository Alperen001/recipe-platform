package com.recipeplatform.config;

import com.recipeplatform.entity.User;
import com.recipeplatform.enums.Gender;
import com.recipeplatform.enums.Role;
import com.recipeplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@example.com";

        if (userRepository.findByEmail(adminEmail).isEmpty()) {
            User admin = User.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode("123456"))
                    .name("Admin")
                    .surName("User")
                    .gender(Gender.ERKEK)
                    .role(Role.ADMIN)
                    .userName("admin")
                    .build();
            userRepository.save(admin);
        }
    }
}