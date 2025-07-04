package com.scaler.splitwise.splitwise.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserExpense extends BaseModel{
    @ManyToOne
    private User user;
    @ManyToOne
    private Expense expense;
    private UserExpenseType userExpenseType;
    private int amount;
}
