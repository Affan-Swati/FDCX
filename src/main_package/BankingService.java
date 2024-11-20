package main_package;

public interface BankingService 
{
 // Deposit funds into a bank account
 void deposit(String accountNumber, double amount);

 // Withdraw funds from a bank account
 boolean withdraw(String accountNumber, double amount);

 // Transfer funds between accounts
 boolean transfer(String fromAccountNumber, String toAccountNumber, double amount);

 // Check the balance of a bank account
 double checkBalance(String accountNumber);

 // View transaction history for an account
 void viewTransactionHistory(String accountNumber);
}

