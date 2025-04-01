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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeCategoryController {

    HomeCategoryService homeCategoryService;
    HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<Home> createHomeCategory(@RequestBody List<HomeCategory> homeCategoryList) {

        List<HomeCategory> categories = homeCategoryService.createCategories(homeCategoryList);
        Home home = homeService.createHomePageData(categories);

        return new ResponseEntity<>(home, HttpStatus.CREATED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() {

        List<HomeCategory> categories = homeCategoryService.getAllHomeCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id, @RequestBody HomeCategory homeCategory) throws Exception {

        HomeCategory updateHomeCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return new ResponseEntity<>(updateHomeCategory, HttpStatus.OK);
    }
}
