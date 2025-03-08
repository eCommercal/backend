package com.viet.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateProductRequest {
    String title;
    String description;
    int mrpPrice;
    int sellingPrice;
    String color;
    List<String> images;
    String category;
    String category2;
    String category3;
    String sizes;
}
