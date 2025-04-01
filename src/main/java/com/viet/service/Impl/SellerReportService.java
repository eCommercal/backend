package com.viet.service.Impl;

import com.viet.model.Seller;
import com.viet.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
