package com.viet.service;

import com.viet.domain.HomeCategorySection;
import com.viet.model.Deal;
import com.viet.model.Home;
import com.viet.model.HomeCategory;
import com.viet.repository.DealRepository;
import com.viet.service.Impl.HomeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeServiceImpl implements HomeService {

    DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {
        List<HomeCategory> gridCategories = allCategories.stream().filter(
                category -> category.getSection() == HomeCategorySection.GRID
        ).collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream().filter(
                category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES
        ).collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream().filter(
                category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES
        ).collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream().filter(
                category -> category.getSection() == HomeCategorySection.DEALS
        ).collect(Collectors.toList());

        List<Deal> createDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream().filter(
                    category -> category.getSection() == HomeCategorySection.DEALS
            ).map(category -> new Deal(null, 10, category)
            ).collect(Collectors.toList());
            createDeals = dealRepository.saveAll(deals);
        } else {
            createDeals = dealRepository.findAll();
        }

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDealCategories(dealCategories);
        home.setDeals(createDeals);

        return home;
    }
}
