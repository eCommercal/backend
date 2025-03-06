package com.viet.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Home {
    List<HomeCategory> grid;
    List<HomeCategory> shopByCategories;
    List<HomeCategory> electricCategories;
    List<HomeCategory> dealCategories;
}
