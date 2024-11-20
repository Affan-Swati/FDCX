package main_package;

public class Stock 
{
    private String name;
    private float unitPrice;
    private DateTime dateTime;
    private String purchase_time;

    public Stock(String name, float unitPrice) 
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.dateTime = new DateTime();
        this.purchase_time = dateTime.toString();
    }

    public void getPrediction() 
    {
        // Predict stock trends
    }
}
