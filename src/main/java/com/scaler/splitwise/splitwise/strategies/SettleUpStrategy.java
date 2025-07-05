package com.scaler.splitwise.splitwise.strategies;

import com.scaler.splitwise.splitwise.models.Expense;

import java.util.List;

public interface SettleUpStrategy {
    List<Transaction> settleUp(List<Expense> expenses);
}
