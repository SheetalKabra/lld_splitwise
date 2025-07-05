package com.scaler.splitwise.splitwise.services;

import com.scaler.splitwise.splitwise.strategies.Transaction;

import java.util.List;

public interface ExpenseService {
    List<Transaction> settleUpUser(Long userId);
}
