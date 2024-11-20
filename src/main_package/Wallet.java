package main_package;

import java.util.UUID;

public class Wallet 
{
    private float balance;
    private String walletID;

    public Wallet()
    {
    	this.balance = 0;
    	this.walletID = UUID.randomUUID().toString();
    }
    
    public float getBalance() 
    {
        return balance;
    }
    
    public String getWalletID() 
    {
        return walletID;
    }

    public void addFunds(float amount) 
    {
        balance += amount;
    }

    public void withdrawFunds(float amount) 
    {
    	if(amount < 0)
    	{
    		System.out.println("Amount Must Be Greator Than Zero!");
    		return;
    	}
    		
        if (amount <= balance) 
        {
            balance -= amount;
        }
        else 
        {
            System.out.println("Insufficient balance.");
        }
    }
}
