package com.serviceImpl;

import com.beans.Account;
import com.beans.CreditAccount;
import com.beans.DebitAccount;
import com.exceptions.AccountNotFoundException;
import com.exceptions.DBException;
import com.exceptions.InsufficientFundsException;
import com.helpers.ConnectionUtility;
import com.services.IAccountDBService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountsDBServiceImpl implements IAccountDBService {

    Connection conn;
    @Override
    public void addAccount(Account account) throws DBException {
        try{
            conn = ConnectionUtility.getConnection();
            String sql = "insert into accounts (account_num,account_owner,balance,account_type,password,bonus_point,credit_limit) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1,account.getAccountNum());
            pstmt.setString(2,account.getAccountOwner());
            pstmt.setDouble(3, account.getBalance());
            if(account instanceof CreditAccount)
            {
                CreditAccount ca = (CreditAccount) account;
                pstmt.setString(4,"CREDIT");
                pstmt.setString(5,null);
                pstmt.setInt(6,ca.getBonusPoint());
                pstmt.setDouble(7,ca.getLimit());
            } else if (account instanceof DebitAccount) {
                DebitAccount da = (DebitAccount) account;
                pstmt.setString(4,"DEBIT");
                pstmt.setString(5,da.getPassword());
                pstmt.setInt(6,0);
                pstmt.setDouble(7,0);
            }else{
                throw new DBException("Only CREDIT and DEBIT accounts are supported");
            }
            pstmt.executeUpdate();
        }catch (SQLException e){
            throw new DBException("Error creating account : "+e.getMessage());
        }
    }

    @Override
    public void displayAllAccounts() throws DBException {
        String sql = "SELECT * FROM accounts ORDER BY account_num";
        List<Account> accounts = new ArrayList<>();
        try{
            conn = ConnectionUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
                accounts.add(ResultSettoAccount(rs));
            }
        }catch (SQLException e)
        {
            throw new DBException("Cannot retrieve account : "+e.getMessage());
        }
        if(accounts.isEmpty()){
            System.out.println("No accounts found in DB");
        }else{
            System.out.println("All accounts");
            for(int i=0;i<accounts.size();i++){
                System.out.println((i+1)+" . "+accounts.get(i).toString());
            }
        }
    }

    @Override
    public Account findAccountById(String accountNum) throws AccountNotFoundException {
        String sql="SELECT * FROM accounts WHERE account_num = ?";
        try{
            Connection conn = ConnectionUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,accountNum);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return ResultSettoAccount(rs);
            }else{
                throw new AccountNotFoundException("Account not found having id : "+accountNum);
            }
        }catch (SQLException e){
            throw new AccountNotFoundException("Could not find account"+e.getMessage());
        }
    }

    @Override
    public void performPaymentById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException, DBException {
        Account account = findAccountById(accountNum);
        if(account instanceof DebitAccount){
            ((DebitAccount) account).performPayment(amount,password);
        }else{
            account.performPayment(amount);
        }
        updateAccount(account);
    }

    @Override
    public void performDepositById(String accountNum, double amount, String password) throws AccountNotFoundException, InsufficientFundsException, DBException {
        Account account = findAccountById(accountNum);
        if(account instanceof DebitAccount){
            ((DebitAccount) account).performDeposit(amount,password);
        }else{
            account.performDeposit(amount);
        }
        updateAccount(account);
    }

    @Override
    public void deleteAccountById(String accountNum) throws AccountNotFoundException {
        String sql = "DELETE FROM accounts WHERE account_num = ?";
        try{
            Connection conn = ConnectionUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,accountNum);
            int res = pstmt.executeUpdate();
            if(res==0){
                throw new AccountNotFoundException("Account not found for deletion");
            }
            System.out.println("Account deleted successfully");
        }catch (SQLException e){
            throw new AccountNotFoundException("Error deleting"+e.getMessage());
        }
    }

    private void updateAccount(Account account) throws DBException {
        String sql="UPDATE accounts SET account_owner = ?, balance = ?, password = ?,bonus_point = ?,credit_limit = ? WHERE account_num = ?";
        try{
            Connection conn = ConnectionUtility.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,account.getAccountOwner());
            pstmt.setDouble(2,account.getBalance());
            if(account instanceof CreditAccount)
            {
                CreditAccount ca = (CreditAccount) account;
                pstmt.setString(3,null);
                pstmt.setInt(4,ca.getBonusPoint());
                pstmt.setDouble(5,ca.getLimit());
            } else if (account instanceof DebitAccount) {
                DebitAccount da = (DebitAccount) account;
                pstmt.setString(3,da.getPassword());
                pstmt.setInt(4,0);
                pstmt.setDouble(5,0);
            }else{
                throw new DBException("Only CREDIT and DEBIT accounts are supported");
            }
            pstmt.setString(6,account.getAccountNum());
            if(pstmt.executeUpdate() == 0){
                throw new DBException("Account not found for update");
            }
        }catch(SQLException e){
            throw new DBException("Error updating account"+e.getMessage());
        }
    }

    private Account ResultSettoAccount(ResultSet rs) throws SQLException {
        String accountNum=rs.getString("account_num");
        String accountOwner=rs.getString("account_owner");
        double balance= rs.getDouble("balance");
        String accountType=rs.getString("account_type");
        String password=rs.getString("password");
        int bonusPoint=rs.getInt("bonus_point");
        double creditLimit=rs.getDouble("credit_limit");
        switch (accountType){
            case "CREDIT":
                return new CreditAccount(accountNum,accountOwner,balance,bonusPoint,creditLimit);
            case "DEBIT":
                return new DebitAccount(accountNum,accountOwner,balance,password);
            default:
                throw new SQLException("Unsupported account type");
        }
    }
}
