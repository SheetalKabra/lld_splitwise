package com.scaler.splitwise.splitwise.dtos;

import com.scaler.splitwise.splitwise.strategies.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SettleUpUserResponseDto {
    private String message;
    private String status;
    private List<Transaction> transactions;
}
