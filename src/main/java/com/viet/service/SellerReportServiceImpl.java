package com.viet.service;

import com.viet.model.Seller;
import com.viet.model.SellerReport;
import com.viet.repository.SellerReportRepository;
import com.viet.service.Impl.SellerReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerReportServiceImpl implements SellerReportService {

    SellerReportRepository repository;

    @Override
    public SellerReport getSellerReport(Seller seller) {

        SellerReport sellerReport = repository.findBySellerId(seller.getId());

        if(sellerReport == null) {
            SellerReport newSellerReport = new SellerReport();
            newSellerReport.setSeller(seller);
            return repository.save(newSellerReport);
        }

        return sellerReport;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return repository.save(sellerReport);
    }
}
