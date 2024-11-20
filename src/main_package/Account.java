package main_package;
import java.util.*;

public class Account 
{
    private List<Stock> stocks = new ArrayList<>();
    private String transactionHistory;
    private Wallet wallet;
    private Subscription subscription;
    private int loyaltyPoints;
    
    
    public Account()
    {
    	 this.wallet = new Wallet();
    	 this.loyaltyPoints = 0;
    }
    
    public Wallet getWallet() 
    {
        return wallet;
    }

    public String getTransactionHistory() 
    {
        return transactionHistory;
    }

    public void claimLoyaltyPoints(int points) 
    {
        loyaltyPoints += points;
    }
    
    public int getLoyaltyPoints() 
    {
        return loyaltyPoints;
    }
	
}
