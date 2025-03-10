package com.viet.service;

import com.viet.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Long sellerId);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
