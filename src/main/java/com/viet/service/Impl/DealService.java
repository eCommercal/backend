package com.viet.service.Impl;

import com.viet.model.Deal;

import java.util.List;

public interface DealService {
    List<Deal> getAllDeals();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal, Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;
}
