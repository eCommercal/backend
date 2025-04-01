package com.viet.service.Impl;

import com.viet.model.Order;
import com.viet.model.Seller;
import com.viet.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Order order);
    List<Transaction> getTransactionBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
