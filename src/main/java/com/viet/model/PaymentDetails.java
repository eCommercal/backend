package com.viet.model;

import com.viet.domain.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentDetails {
    String paymentId;
    String code;
    String message;
    String payment_url;
    PaymentStatus status;
}
