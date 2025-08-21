package com.services;

import com.beans.Account;
import com.exceptions.AccountNotFoundException;
import com.exceptions.InsufficientFundsException;

public interface IAccountService {
    void addAccount(Account account);
    void displayAllAccounts();
    Account findAccountById(String accountNum) throws AccountNotFoundException;
    void performPaymentById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException;
    void performDepositById(String accountNum, double amount, String password) throws AccountNotFoundException;
    void deleteAccountById(String accountNum) throws AccountNotFoundException;

}
