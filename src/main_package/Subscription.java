package main_package;
import java.time.*;

public class Subscription 
{
    private String name;
    private String type; // monthly , quarterly , yearly, cancelled
    private float price;
    private LocalDate renewalDate;
    private DateTime dateTime;

    
    public void subscribe(String type)
    {
    	this.type = type;
    	this.renewSubscription();
    }
    
    public void renewSubscription() 
    {
        if(this.type == "monthly")
        {
        	renewalDate = dateTime.getDateAfterMonths(1);
        }
        
        else if (this.type == "quarterly")
        {
        	renewalDate = dateTime.getDateAfterMonths(3);
        }
        
        else if(this.type == "yearly")
        	renewalDate = dateTime.getDateAfterMonths(12);
    }

    public void cancelSubscription() 
    {
        
    }

    public void changeSubscription(String newType) 
    {
        this.type = newType;
    }
}