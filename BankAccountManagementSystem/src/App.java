import com.beans.Account;
import com.beans.DebitAccount;
import com.controllers.AccountController;
import com.controllers.AccountDBController;

import java.util.Scanner;

public class App {
//   static AccountController controller = new AccountController();
     private static AccountDBController controller = new AccountDBController();
     private static Scanner sc = new Scanner(System.in);

   public static void displayMenu(){
       System.out.println("Welcome to Standard Chartered Bank.");
       System.out.println("Please enter your choice");
       System.out.println("1 for Add new account");
       System.out.println("2 for Display all accounts");
       System.out.println("3 for Display accounts by ID");
       System.out.println("4 for Perform payment");
       System.out.println("5 for Perform deposit");
       System.out.println("6 for Delete selected account");
       System.out.println("7 for Exit the bank application");
   }

   public static void handleChoice(){
       int choice = sc.nextInt();
       sc.nextLine();
       switch (choice){
           case 1:
               addNewAccount();
               break;
           case 2:
               controller.displayAllAccounts();
               break;
           case 3:
               displayAccountById();
               break;
           case 4:
               performPayment();
               break;
           case 5:
               performDeposit();
               break;
           case 6:
               deleteAccount();
               break;
           case 7:
               System.out.println("Exited the application.");
               System.exit(0);
               break;
           default:
               System.out.println("Invalid choice.");
       }
   }

   public static void addNewAccount(){
       System.out.println("Please enter account details");
       System.out.println("Enter account type(Credit or Debit): ");
       String type = sc.nextLine();
       System.out.println("Enter account number: ");
       String accountNum = sc.nextLine();
       System.out.println("Enter account owner : ");
       String owner = sc.nextLine();
       System.out.println("Enter balance : ");
       double balance = sc.nextDouble();
       sc.nextLine();
       String password = "";
       double limit = 0;
       if(type.equalsIgnoreCase("credit"))
       {
           System.out.println("Enter Limit : ");
           limit = sc.nextDouble();
           sc.nextLine();
       } else if (type.equalsIgnoreCase("debit")) {
           System.out.println("Enter password :");
           password = sc.nextLine();
       }
       controller.addNewAccount(type,accountNum, owner, balance,password,limit);
   }
   public static void displayAccountById(){
       System.out.println("Please enter the account id : ");
       String accountNum = sc.nextLine();
       controller.displayAccountById(accountNum);
   }
   public static void performPayment(){
       System.out.println("Please enter the account id : ");
       String accountNum = sc.nextLine();
       System.out.println("Please enter the amount : ");
       double amount = sc.nextDouble();
       sc.nextLine();
       try{
           Account account = controller.accountService.findAccountById(accountNum);
           String password = "";
           if(account instanceof DebitAccount)
           {
               System.out.println("Enter password :");
               password = sc.nextLine();
           }
           controller.performPayment(accountNum,amount,password);
       }catch(Exception e){
           System.out.println("Account not found");
       }
   }
    public static void performDeposit(){
        System.out.println("Please enter the account id : ");
        String accountNum = sc.nextLine();
        System.out.println("Please enter the amount : ");
        double amount = sc.nextDouble();
        sc.nextLine();
        try{
            Account account = controller.accountService.findAccountById(accountNum);
            String password = "";
            if(account instanceof DebitAccount)
            {
                System.out.println("Enter password :");
                password = sc.nextLine();
            }
            controller.performDeposit(accountNum,amount,password);
        }catch(Exception e){
            System.out.println("Account not found");
        }
    }
    public static void deleteAccount(){
        System.out.println("Please enter the account id : ");
        String accountNum = sc.nextLine();
        controller.deleteAccount(accountNum);
    }

    public static void main(String[] args) {
        while(true)
        {
            displayMenu();
            handleChoice();
        }
    }

}
