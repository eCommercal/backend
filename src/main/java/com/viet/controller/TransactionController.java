package com.viet.controller;

import com.viet.model.Seller;
import com.viet.model.Transaction;
import com.viet.service.Impl.SellerService;
import com.viet.service.Impl.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transactions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionController {

    TransactionService transactionService;
    SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getAllSellerTransactions(@RequestHeader("Authorization") String jwt) throws Exception {

        Seller seller = sellerService.getSellerProfile(jwt);

        List<Transaction> transactions = transactionService.getTransactionBySellerId(seller);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Transaction>> getAllTransaction()  {

        List<Transaction> transactions = transactionService.getAllTransactions();

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
