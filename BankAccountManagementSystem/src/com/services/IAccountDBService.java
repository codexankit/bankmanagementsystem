package com.services;

import com.beans.Account;
import com.exceptions.AccountNotFoundException;
import com.exceptions.DBException;
import com.exceptions.InsufficientFundsException;

import java.util.zip.DataFormatException;

public interface IAccountDBService {
    void addAccount(Account account) throws DBException;
    void displayAllAccounts() throws DBException;
    Account findAccountById(String accountNum) throws AccountNotFoundException;
    void performPaymentById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException, DBException;
    void performDepositById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException, DBException;
    void deleteAccountById(String accountNum) throws AccountNotFoundException;
}
