package com.beans;

import com.exceptions.InsufficientFundsException;

public class CreditAccount extends Account{
    int bonusPoint;
    double limit;

    public CreditAccount() {
    }

    public CreditAccount(String accountNum, String accountOwner, double balance, int bonusPoint, double limit) {
        super(accountNum, accountOwner, balance);
        this.bonusPoint = bonusPoint;
        this.limit = limit;
    }

    public int getBonusPoint() {
        return bonusPoint;
    }

    public void setBonusPoint(int bonusPoint) {
        this.bonusPoint = bonusPoint;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    @Override
    public void performPayment(double amount) throws InsufficientFundsException {
        if(amount > limit)
        {
            throw new InsufficientFundsException("Amount exceeds limit on your card of : "+limit);
        }else if(amount > balance){
            throw new InsufficientFundsException("Insufficient balance");
        }else{
            balance = balance - amount;
            bonusPoint = bonusPoint + (int)(amount/100);
            System.out.println("Credit payment of "+amount+" completed. New balance : "+balance+" Bonus points : "+bonusPoint);
        }
    }

    @Override
    public void performDeposit(double amount) {
        balance = balance + amount;
        bonusPoint = bonusPoint + (int)(amount/100);
        System.out.println("Credit deposit of "+amount+" completed. New balance : "+balance+" Bonus points : "+bonusPoint);
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "bonusPoint=" + bonusPoint +
                ", limit=" + limit +
                ", accountNum='" + accountNum + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", balance=" + balance +
                '}';
    }

}
