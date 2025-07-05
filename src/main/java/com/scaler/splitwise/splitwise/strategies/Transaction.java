package com.scaler.splitwise.splitwise.strategies;

import com.scaler.splitwise.splitwise.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private User from;
    private User to;
    private Integer amount;
}
