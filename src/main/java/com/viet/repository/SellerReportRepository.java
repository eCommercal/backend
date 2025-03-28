package com.viet.repository;

import com.viet.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(Long sellerId);
}
