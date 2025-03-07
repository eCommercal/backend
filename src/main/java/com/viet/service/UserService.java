package com.viet.service;

import com.viet.model.User;

public interface UserService {
    User findUserByEmail(String email) throws Exception;
    User findUserByJwtToken(String jwt) throws Exception;
}
