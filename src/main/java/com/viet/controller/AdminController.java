package com.viet.controller;

import com.viet.domain.AccountStatus;
import com.viet.model.Seller;
import com.viet.service.Impl.SellerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id, @PathVariable AccountStatus status) throws Exception {
        Seller updateSeller = sellerService.updateAccountStatus(id, status);
        return new ResponseEntity<>(updateSeller, HttpStatus.OK);
    }
}
