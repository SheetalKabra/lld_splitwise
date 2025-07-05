package com.scaler.splitwise.splitwise.services;

import com.scaler.splitwise.splitwise.models.Expense;
import com.scaler.splitwise.splitwise.models.User;
import com.scaler.splitwise.splitwise.models.UserExpense;
import com.scaler.splitwise.splitwise.repositories.UserExpenseRepository;
import com.scaler.splitwise.splitwise.repositories.UserRepository;
import com.scaler.splitwise.splitwise.strategies.SettleUpStrategy;
import com.scaler.splitwise.splitwise.strategies.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExpenseServiceImpl implements ExpenseService{
    private final UserRepository userRepository;
    private final UserExpenseRepository userExpenseRepository;
    private final SettleUpStrategy settleUpStrategy;

    @Autowired
    public ExpenseServiceImpl(
            UserRepository userRepository,
            UserExpenseRepository userExpenseRepository,
            @Qualifier("twoSetsSettleUpStrategy") SettleUpStrategy settleUpStrategy) {
        this.userRepository = userRepository;
        this.userExpenseRepository = userExpenseRepository;
        this.settleUpStrategy = settleUpStrategy;
    }

    @Override
    public List<Transaction> settleUpUser(Long userId) {
        //get user from userId
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            //throw exception
            return null;
        }
        //get all expenses from userExpense table based on user table
        List<UserExpense> userExpenses = userExpenseRepository.findAllByUserId(userId);
        List<Expense> expenses = new ArrayList<>();
        for(UserExpense userExpense: userExpenses){
            expenses.add(userExpense.getExpense());
        }

        List<Transaction> transactions = settleUpStrategy.settleUp(expenses);

        List<Transaction> filteredTransactions = new ArrayList<>();
        for(Transaction transaction: transactions){
            if(transaction.getFrom().equals(optionalUser.get()) || transaction.getTo().equals(optionalUser.get())){
                filteredTransactions.add(transaction);
            }
        }
        return filteredTransactions;

    }
}
