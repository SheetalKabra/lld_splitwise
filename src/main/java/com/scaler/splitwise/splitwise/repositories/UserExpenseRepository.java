package com.scaler.splitwise.splitwise.repositories;

import com.scaler.splitwise.splitwise.models.Expense;
import com.scaler.splitwise.splitwise.models.UserExpense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserExpenseRepository extends JpaRepository<UserExpense, Long> {
    List<UserExpense> findAllByUserId(Long userId);
    List<UserExpense> findAllByExpenseIn(List<Expense> expenses);

}
