package com.viet.controller;

import com.viet.domain.USER_ROLE;
import com.viet.model.User;
import com.viet.model.VerificationCode;
import com.viet.repository.UserRepository;
import com.viet.request.LoginOtpRequest;
import com.viet.request.LoginRequest;
import com.viet.request.SignupRequest;
import com.viet.response.ApiResponse;
import com.viet.response.AuthResponse;
import com.viet.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception {

        AuthResponse authResponse = authService.signing(req);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody SignupRequest req) throws Exception {

        String jwt = authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);


        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/send-otp")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody LoginOtpRequest req) throws Exception {

        authService.sendOtp(req.getEmail(), req.getRole());

        ApiResponse res = new ApiResponse();

        res.setMessage("otp sent success");


        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
