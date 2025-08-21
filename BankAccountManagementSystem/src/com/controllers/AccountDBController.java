package com.controllers;

import com.beans.Account;
import com.beans.CreditAccount;
import com.beans.DebitAccount;
import com.exceptions.AccountNotFoundException;
import com.exceptions.DBException;
import com.exceptions.InsufficientFundsException;
import com.serviceImpl.AccountServiceImpl;
import com.serviceImpl.AccountsDBServiceImpl;
import com.services.IAccountDBService;
import com.services.IAccountService;

public class AccountDBController {
    public IAccountDBService accountService;

    public AccountDBController(){
        accountService = new AccountsDBServiceImpl();
    }

    public void addNewAccount(String type, String accountNum, String owner, double balance, String password, double limit){
        try{
            Account account;
            if(type.equalsIgnoreCase("credit")){
                account = new CreditAccount(accountNum, owner, balance, 0, limit);
            }else if(type.equalsIgnoreCase("debit")){
                account = new DebitAccount(accountNum, owner, balance, password);
            }else{
                System.out.println("Only and credit and debit accounts supported");
                return;
            }
            accountService.addAccount(account);
        }catch (DBException e){
            System.out.println("Error adding account"+e.getMessage());
        }
    }
    public void displayAllAccounts(){
        try{
            accountService.displayAllAccounts();
        }catch (DBException e){
            System.out.println("Error retrieving accounts"+e.getMessage());
        }
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
            accountService.performPaymentById(accountNum,amount,password);
        }catch (InsufficientFundsException | DBException | AccountNotFoundException e) {
            System.out.println("Payment failed"+e.getMessage());;
        }
    }
    public void performDeposit(String accountNum, double amount, String password){
        try{
            accountService.performDepositById(accountNum,amount,password);
        }catch ( InsufficientFundsException | DBException | AccountNotFoundException e) {
            System.out.println("Payment failed"+e.getMessage());;
        }
    }
    public void deleteAccount(String accountNum){
        try{
            accountService.deleteAccountById(accountNum);
        }catch(AccountNotFoundException e){
            System.out.println(e.getMessage());
        }
    }

    public String getAccountType(String accountNum){
        try{
            Account account = accountService.findAccountById(accountNum);
            if(account instanceof DebitAccount){
                return "DEBIT";
            } else if (account instanceof CreditAccount) {
                return "CREDIT";
            }else{
                return "UNSUPPORTED";
            }
        }catch (AccountNotFoundException e){
            return "NOT_FOUND";
        }
    }
}
