package com.viet.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddItemRequest {
    String size;
    int quantity;
    Long productId;
}
