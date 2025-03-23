package com.viet.service;

import com.viet.model.Deal;
import com.viet.model.HomeCategory;
import com.viet.repository.DealRepository;
import com.viet.repository.HomeCategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DealServiceImpl implements DealService {

    DealRepository dealRepository;
    HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {

        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);


        Deal newDeal = new Deal();
        newDeal.setCategory(homeCategory);
        newDeal.setDiscount(deal.getDiscount());

        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal existingDeal = dealRepository.findById(id).orElse(null);

        HomeCategory homeCategory = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        if(existingDeal != null) {
            if(deal.getDiscount() != null) {
                existingDeal.setDiscount(deal.getDiscount());
            }

            if (homeCategory != null) {
                existingDeal.setCategory(homeCategory);
            }
            return dealRepository.save(existingDeal);
        }
       throw new Exception("deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal = dealRepository.findById(id).orElseThrow(
                () -> new Exception("deal not found")
        );

        dealRepository.delete(deal);
    }
}
