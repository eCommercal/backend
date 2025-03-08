package com.viet.service;

import com.viet.config.JwtProvider;
import com.viet.domain.AccountStatus;
import com.viet.domain.USER_ROLE;
import com.viet.exception.SellerException;
import com.viet.model.Address;
import com.viet.model.Seller;
import com.viet.repository.AddressRepository;
import com.viet.repository.SellerRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerServiceImpl implements SellerService {

    SellerRepository sellerRepository;
    JwtProvider jwtProvider;
    PasswordEncoder passwordEncoder;
    AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);

    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {

        Seller existingSeller = sellerRepository.findByEmail(seller.getEmail());
        if (existingSeller != null) {
            throw new Exception("Seller already existed !!");
        }

        Address saveAddress = addressRepository.save(seller.getPickupAddress());

        Seller saveSeller = new Seller();
        saveSeller.setEmail(seller.getEmail());
        saveSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        saveSeller.setPickupAddress(saveAddress);
        saveSeller.setGSTIN(seller.getGSTIN());
        saveSeller.setSellerName(seller.getSellerName());
        saveSeller.setRole(USER_ROLE.ROLE_SELLER);
        saveSeller.setMobile(seller.getMobile());
        saveSeller.setBankDetails(seller.getBankDetails());
        saveSeller.setBusinessDetails(seller.getBusinessDetails());


        return sellerRepository.save(saveSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new SellerException("seller not found with id " + id));
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {

        Seller seller = sellerRepository.findByEmail(email);

        if(seller == null) {
            throw new Exception("seller not found with email " + email);
        }

        return seller;
    }

    @Override
    public List<Seller> findAll(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {

        Seller existingSeller = this.getSellerById(id);

        if(seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }

        if(seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }
        if(seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }

        if(seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null) {
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }

        if(seller.getBankDetails() != null
                && seller.getBankDetails().getAccountHolderName() != null
                && seller.getBankDetails().getIfscCode() != null
                && seller.getBankDetails().getAccountNumber() != null
        ) {
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setIfscCode(seller.getBankDetails().getIfscCode());
            existingSeller.getBankDetails().setAccountNumber(seller.getBankDetails().getAccountNumber());

        }

        if(seller.getPickupAddress() != null
                && seller.getPickupAddress().getAddress() != null
                && seller.getPickupAddress().getMobile() != null
                && seller.getPickupAddress().getCity() != null
                && seller.getPickupAddress().getState() != null
        ) {
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
            existingSeller.getPickupAddress().setPinCode(seller.getPickupAddress().getPinCode());
        }


        return sellerRepository.save(existingSeller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = this.getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateAccountStatus(Long id, AccountStatus status) throws Exception {
        Seller seller = this.getSellerById(id);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = this.getSellerById(id);
        sellerRepository.delete(seller);
    }
}
