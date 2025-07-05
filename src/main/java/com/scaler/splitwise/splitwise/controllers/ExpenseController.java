package com.scaler.splitwise.splitwise.controllers;

import com.scaler.splitwise.splitwise.dtos.SettleUpUserRequestDto;
import com.scaler.splitwise.splitwise.dtos.SettleUpUserResponseDto;
import com.scaler.splitwise.splitwise.services.ExpenseService;
import com.scaler.splitwise.splitwise.strategies.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExpenseController {
    private final ExpenseService expenseService;

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    public SettleUpUserResponseDto settleUp(SettleUpUserRequestDto settleUpUserRequestDto){
        Long userId = settleUpUserRequestDto.getUserId();
        List<Transaction> transactions = expenseService.settleUpUser(userId);
        SettleUpUserResponseDto settleUpUserResponseDto = new SettleUpUserResponseDto();
        settleUpUserResponseDto.setStatus("SUCCESS");
        settleUpUserResponseDto.setTransactions(transactions);
        return settleUpUserResponseDto;
    }

}
