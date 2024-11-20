package main_package;

public class Wallet 
{
    private float balance;

    public float getBalance() 
    {
        return balance;
    }

    public void addFunds(float amount) 
    {
        balance += amount;
    }

    public void withdrawFunds(float amount) 
    {
        if (amount <= balance) 
        {
            balance -= amount;
        } else 
        {
            System.out.println("Insufficient balance.");
        }
    }
}
