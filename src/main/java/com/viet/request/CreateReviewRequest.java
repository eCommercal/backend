package com.viet.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateReviewRequest {
    String reviewText;
    double reviewRating;
    List<String> productImages;
}
