package com.beans;

import com.exceptions.InsufficientFundsException;

public class DebitAccount extends Account{
    String password;

    public DebitAccount() {
    }

    public DebitAccount(String accountNum, String accountOwner, double balance, String password) {
        super(accountNum, accountOwner, balance);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void performPayment(double amount, String password) throws InsufficientFundsException {
        if(!this.password.equals(password))
        {
            System.out.println("Invalid password");
        }else if(amount>balance){
            throw new InsufficientFundsException("Insufficient funds");
        }else{
            balance = balance - amount;
            System.out.println("Debit payment of "+amount+" completed. New balance :"+balance);
        }
    }

    public void performDeposit(double amount, String password) {
        if(this.password.equals(password))
        {
            balance = balance + amount;
            System.out.println("Debit deposit of "+amount+" completed. New balance : "+balance);
        }else{
            System.out.println("Invalid password");
        }
    }

    @Override
    public String toString() {
        return "DebitAccount{" +
                "password='" + password + '\'' +
                ", accountNum='" + accountNum + '\'' +
                ", accountOwner='" + accountOwner + '\'' +
                ", balance=" + balance +
                '}';
    }


}
