package com.viet.service;

import com.viet.request.LoginRequest;
import com.viet.request.SignupRequest;
import com.viet.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;

    void sendOtp(String email) throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;
}
