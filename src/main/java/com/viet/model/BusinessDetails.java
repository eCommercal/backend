package com.viet.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessDetails {
    String businessName;
    String businessAddress;
    String businessMobile;
    String businessEmail;
    String logo;
    String banner;
}
