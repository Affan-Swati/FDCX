package main_package;

public class Stock 
{
    private String name;
    private float unitPrice;
    private DateTime dateTime;
    private String purchase_time;
    private boolean available;

    public Stock(String name, float unitPrice) 
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.dateTime = new DateTime();
        this.available = true;
    }

    public void purchaseStock()
    {
        this.purchase_time = dateTime.toString();
        this.available = false;
    }
    
    public void getPrediction() 
    {
        // Predict stock trends
    }
}
