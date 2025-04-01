package com.viet.controller;

import com.viet.model.Home;
import com.viet.model.HomeCategory;
import com.viet.service.Impl.HomeCategoryService;
import com.viet.service.Impl.HomeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {
    HomeCategoryService homeCategoryService;
    HomeService homeService;

//    @GetMapping("/home-page")
//    public ResponseEntity<Home> getHomePageData () {
//        Home home
//    }

//    @PostMapping("/home/categories")
//    public ResponseEntity<Home> createHomeCategories (@RequestBody List<HomeCategory> homeCategories) {
//
//        List <HomeCategory> categories = homeCategoryService.createCategories(homeCategories);
//
//        Home home = homeService.createHomePageData(categories);
//
//        return new ResponseEntity<>(home, HttpStatus.OK);
//    }
}
