package com.viet.controller;

import com.viet.model.User;
import com.viet.request.SignupRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody SignupRequest req) {

        User user = new User();
        user.setEmail(req.getEmail());
        user.setFullName(req.getFullName());

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
