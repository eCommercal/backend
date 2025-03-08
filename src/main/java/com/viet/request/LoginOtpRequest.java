package com.viet.request;

import com.viet.domain.USER_ROLE;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginOtpRequest {
    String email;
    String otp;
    USER_ROLE role;
}
