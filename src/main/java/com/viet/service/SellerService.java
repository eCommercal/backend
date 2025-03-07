package com.viet.service;

import com.viet.domain.AccountStatus;
import com.viet.model.Seller;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws Exception;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> findAll(AccountStatus status);
    Seller updateSeller(Long id, Seller seller) throws Exception;
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateAccountStatus(Long id, AccountStatus status) throws Exception;
    void deleteSeller(Long id) throws Exception;
}
