package com.viet.service;

import com.viet.domain.USER_ROLE;
import com.viet.model.User;
import com.viet.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitialization implements CommandLineRunner {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        initializeUsers();
    }

    private void initializeUsers() {
        String adminUsername = "lethanhviet1111235@gmail.com";

        if(userRepository.findByEmail(adminUsername) == null) {
            User admin = new User();

            admin.setEmail(adminUsername);
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setFullName("viet");
            admin.setRole(USER_ROLE.ROLE_ADMIN);

            userRepository.save(admin);

        }

    }
}
