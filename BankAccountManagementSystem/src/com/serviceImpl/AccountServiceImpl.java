package com.serviceImpl;

import com.beans.Account;
import com.beans.CreditAccount;
import com.beans.DebitAccount;
import com.exceptions.AccountNotFoundException;
import com.exceptions.InsufficientFundsException;
import com.services.IAccountService;

import java.util.ArrayList;

public class AccountServiceImpl implements IAccountService {
    private ArrayList<Account> accounts;

    public AccountServiceImpl(){
        accounts = new ArrayList<Account>();
    }
    @Override
    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account added successfully");
    }

    @Override
    public void displayAllAccounts() {
        System.out.println("All accounts are listed below : ");
        for(int i=0; i<accounts.size();i++)
        {
            System.out.println((i+1)+") "+accounts.get(i).toString());
        }
    }

    @Override
    public Account findAccountById(String accountNum) throws AccountNotFoundException {
        for(Account account : accounts){
            if(account.getAccountNum().equals(accountNum))
            {
                return account;
            }
        }
        throw new AccountNotFoundException("Account of "+accountNum+" not found.");
    }

    @Override
    public void performPaymentById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException {
        Account account = findAccountById(accountNum);
        if(account instanceof DebitAccount)
        {
            ((DebitAccount) account).performPayment(amount, password);
        }else if(account instanceof CreditAccount) {
            account.performPayment(amount);
        }else{
            account.performPayment(amount);
        }
    }

    @Override
    public void performDepositById(String accountNum, double amount, String password) throws AccountNotFoundException {
        Account account = findAccountById(accountNum);
        if(account instanceof DebitAccount)
        {
            ((DebitAccount) account).performDeposit(amount, password);
        }else if(account instanceof CreditAccount) {
            account.performDeposit(amount);
        }else{
            account.performDeposit(amount);
        }
    }

    @Override
    public void deleteAccountById(String accountNum) throws AccountNotFoundException {
        Account account = findAccountById(accountNum);
        accounts.remove(account);
        System.out.println("Account deleted successfully");
    }
}
