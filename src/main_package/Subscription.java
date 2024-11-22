package main_package;
import java.time.*;

public class Subscription 
{
    private String name;
    private String type; // monthly , quarterly , yearly, cancelled
    private float price;
    private LocalDate renewalDate;
    
    public void subscribe(String type)
    {
    	this.type = type;
    	this.renewSubscription();
    }
    
    public void renewSubscription() 
    {
        if(this.type == "monthly")
        {
        	renewalDate = DateTime.getDateAfterMonths(1);
        }
        
        else if (this.type == "quarterly")
        {
        	renewalDate = DateTime.getDateAfterMonths(3);
        }
        
        else if(this.type == "yearly")
        	renewalDate = DateTime.getDateAfterMonths(12);
    }

    public void cancelSubscription() 
    {
        // TODO:
    }

    public void changeSubscription(String newType) 
    {
        this.type = newType;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public LocalDate getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(LocalDate renewalDate) {
		this.renewalDate = renewalDate;
	}

}