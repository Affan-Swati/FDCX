package main_package;

public class Subscription 
{
    private String name;
    private String type; // monthly , quarterly , yearly
    private float price;
    private DateTime renewalDate;

    public void renewSubscription() 
    {
        // Logic for renewing subscription
    }

    public void cancelSubscription() 
    {
        // Logic for canceling subscription
    }

    public void changeSubscription(String newType) 
    {
        this.type = newType;
    }
}