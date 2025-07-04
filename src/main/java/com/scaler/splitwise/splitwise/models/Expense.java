package com.scaler.splitwise.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Expense extends BaseModel{
    private String description;
    private int amount;
    @Enumerated(EnumType.ORDINAL)
    private ExpenseType expenseType;
    @ManyToOne
    private User createdBy;
    @ManyToOne
    private Group groups;

}
