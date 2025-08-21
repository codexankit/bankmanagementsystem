package com.beans;

import com.exceptions.InsufficientFundsException;

public class Account {
    String accountNum;
    String accountOwner ;
    double balance;

    public Account() {
    }

    public Account(String accountNum, String accountOwner, double balance) {
        this.accountNum = accountNum;
        this.accountOwner = accountOwner;
        this.balance = balance;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getAccountOwner() {
        return accountOwner;
    }

    public void setAccountOwner(String accountOwner) {
        this.accountOwner = accountOwner;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void performPayment(double amount) throws InsufficientFundsException {
        if(amount >balance){
            throw new InsufficientFundsException("Insufficient funds");
        }else{
            balance = balance - amount;
            System.out.println("Payment completed. The available balance is : "+balance);
        }
    }

    public void performDeposit(double amount){
        balance = balance + amount;
        System.out.println("Payment completed. The available balance is : "+balance);
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNum='" + accountNum + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", balance=" + balance +
                '}';
    }

}
