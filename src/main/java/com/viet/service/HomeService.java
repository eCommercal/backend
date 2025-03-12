package com.viet.service;

import com.viet.model.Home;
import com.viet.model.HomeCategory;

import java.util.List;

public interface HomeService {
    Home createHomePageData(List<HomeCategory> allCategories);

}
