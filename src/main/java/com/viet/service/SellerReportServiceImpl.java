package com.viet.service;

import com.viet.model.SellerReport;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerReportServiceImpl implements SellerReportService {


    @Override
    public SellerReport getSellerReport(Long sellerId) {
        return null;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return null;
    }
}
