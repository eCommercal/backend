package com.viet.service;

import com.viet.domain.USER_ROLE;
import com.viet.request.LoginRequest;
import com.viet.request.SignupRequest;
import com.viet.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;

    void sendOtp(String email, USER_ROLE role) throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;
}
