package com.scaler.splitwise.splitwise.strategies;

import com.scaler.splitwise.splitwise.models.Expense;
import com.scaler.splitwise.splitwise.models.User;
import com.scaler.splitwise.splitwise.models.UserExpense;
import com.scaler.splitwise.splitwise.models.UserExpenseType;
import com.scaler.splitwise.splitwise.repositories.UserExpenseRepository;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TwoSetsSettleUpStrategy implements SettleUpStrategy{
    private final UserExpenseRepository userExpenseRepository;

    public TwoSetsSettleUpStrategy(UserExpenseRepository userExpenseRepository) {
        this.userExpenseRepository = userExpenseRepository;
    }

    @Override
    public List<Transaction> settleUp(List<Expense> expenses) {
        //1. from list of expenses, I will first get the all the user expenses from userExpense table who is involved in w.r.t to that expense
        //2. will create a map, having entries against user of how much he paid and how much he has to pay
        //3. Create two tree sets,
        //3.1 where in one set, we will have all the user who has paid extra,
        //3.2 and in 2nd map we will have all the user who has to pay
        //4. finally, we will create a list of transactions where we will stroe the info of userfrom -> userTo, and amount

        List<UserExpense> userExpenseList = userExpenseRepository.findAllByExpenseIn(expenses);
        Map<User, Integer> moneyPaidExtra = new HashMap<>();
        for(UserExpense userExpense: userExpenseList){
            User user = userExpense.getUser();
            int currentPAidAmount = 0;
            if(moneyPaidExtra.containsKey(user)){
                currentPAidAmount += moneyPaidExtra.get(user);
            }

            if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)){
                moneyPaidExtra.put(user, currentPAidAmount + moneyPaidExtra.get(user));
            }else{
                moneyPaidExtra.put(user, currentPAidAmount - moneyPaidExtra.get(user));
            }
        }

        TreeSet<Pair<User, Integer>> extraPaid = new TreeSet<Pair<User, Integer>>();
        TreeSet<Pair<User, Integer>> lessPaid = new TreeSet<Pair<User, Integer>>();
        for(Map.Entry<User, Integer> userAmount : moneyPaidExtra.entrySet()){
            if(userAmount.getValue() >= 0){
                extraPaid.add(new Pair<>(userAmount.getKey(), userAmount.getValue()));
            }else{
                lessPaid.add(new Pair<>(userAmount.getKey(), userAmount.getValue()));
            }
        }

        List<Transaction> transactions = new ArrayList<>();
        while(!lessPaid.isEmpty()){
            Transaction transaction = new Transaction();
            Pair<User, Integer> lessPaidPAir = lessPaid.pollFirst();
            Pair<User, Integer> extraPaidPAir = extraPaid.pollFirst();
            //lessPAidPair : from
            //extraPaidPAir: to
            transaction.setFrom(lessPaidPAir.a);
            transaction.setTo(extraPaidPAir.a);


            if(extraPaidPAir.b > lessPaidPAir.b){
                transaction.setAmount(Math.abs(lessPaidPAir.b));
                //Sheetal: +200, Akhil: -100
                //add extraPaidPair again into TreeSet. Also reduce the amount by 100(which is less paid pair amount)
                if(!(extraPaidPAir.b-Math.abs(lessPaidPAir.b) ==0)){
                    extraPaid.add(new Pair<>(extraPaidPAir.a, extraPaidPAir.b-Math.abs(lessPaidPAir.b)));
                }


            }else{
                transaction.setAmount(extraPaidPAir.b);
                //Sheetal: +100, Akhil: -200
                //add lessPaidPair again into TreeSet. Also reduce the amount by 100(which is less paid pair amount)
                if(!(extraPaidPAir.b+lessPaidPAir.b == 0)){
                    lessPaid.add(new Pair<>(lessPaidPAir.a, extraPaidPAir.b+lessPaidPAir.b));
                }
            }
            transactions.add(transaction);
        }

        return transactions;
    }
}
