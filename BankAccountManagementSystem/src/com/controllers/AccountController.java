package com.controllers;

import com.beans.Account;
import com.beans.CreditAccount;
import com.beans.DebitAccount;
import com.exceptions.AccountNotFoundException;
import com.exceptions.InsufficientFundsException;
import com.serviceImpl.AccountServiceImpl;
import com.services.IAccountService;

public class AccountController {
    public IAccountService accountService;

    public AccountController(){
        accountService = new AccountServiceImpl();
    }

    public void addNewAccount(String type, String accountNum, String owner, double balance, String password, double limit){
        Account account;
        if(type.equalsIgnoreCase("credit")){
            account = new CreditAccount(accountNum, owner, balance, 0, limit);
        }else if(type.equalsIgnoreCase("debit")){
            account = new DebitAccount(accountNum, owner, balance, password);
        }else{
            account = new Account(accountNum, owner, balance);
        }
        accountService.addAccount(account);
    }
     public void displayAllAccounts(){
        accountService.displayAllAccounts();
     }
     public void displayAccountById(String accountNum){
        try{
            Account account = accountService.findAccountById(accountNum);
            System.out.println(account.toString());
        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
     }
     public void performPayment(String accountNum, double amount, String password){
        try{
            Account account = accountService.findAccountById(accountNum);
            if( account instanceof DebitAccount)
            {
                ((DebitAccount) account).performPayment(amount,password);
            }else{
                account.performPayment(amount);
            }
        }catch (AccountNotFoundException e){
            System.out.println(e.getMessage());
        } catch (InsufficientFundsException e) {
            throw new RuntimeException(e);
        }
     }
     public void performDeposit(String accountNum, double amount, String password){
        try{
            Account account = accountService.findAccountById(accountNum);
            if( account instanceof DebitAccount)
            {
                ((DebitAccount) account).performDeposit(amount,password);
            }else{
                account.performDeposit(amount);
            }
        }catch (AccountNotFoundException e)
        {
            System.out.println(e.getMessage());
        }
     }
     public void deleteAccount(String accountNum){
        try{
            accountService.deleteAccountById(accountNum);
        }catch(AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
     }
}
