package com.viet.response;

import com.viet.domain.USER_ROLE;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String jwt;
    String message;
    USER_ROLE role;
}
