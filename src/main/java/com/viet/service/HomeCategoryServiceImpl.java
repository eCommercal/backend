package com.viet.service;

import com.viet.model.HomeCategory;
import com.viet.repository.HomeCategoryRepository;
import com.viet.service.Impl.HomeCategoryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeCategoryServiceImpl implements HomeCategoryService {

    HomeCategoryRepository homeCategoryRepository;

    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) throws Exception {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id).orElseThrow(
                () -> new Exception("category not found")
        );

        if(homeCategory.getImage() != null && !homeCategory.getImage().isEmpty()){
            existingHomeCategory.setImage(homeCategory.getImage());
        }

        if(homeCategory.getCategoryId() != null && !homeCategory.getCategoryId().isEmpty()){
            existingHomeCategory.setCategoryId(homeCategory.getCategoryId());
        }

        return homeCategoryRepository.save(existingHomeCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }
}
