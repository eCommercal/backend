package com.viet.service;

import com.viet.config.JwtProvider;
import com.viet.model.User;
import com.viet.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    JwtProvider jwtProvider;

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user == null) {
            throw new Exception("user not found with email " + email);
        }

        return user;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.findUserByEmail(email);
    }
}
