package main_package;

public class Stock 
{
    private String name;
    private float unitPrice;
    private String purchase_time;
    private boolean available;

    public Stock(String name, float unitPrice) 
    {
        this.name = name;
        this.unitPrice = unitPrice;
        this.available = true;
    }

    public void purchaseStock()
    {
        this.purchase_time = DateTime.getCurrentDate() + DateTime.getCurrentTime();
        this.available = false;
    }
    
    public void getPrediction() 
    {
        // Predict stock trends
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}


	public String getPurchase_time() {
		return purchase_time;
	}

	public void setPurchase_time(String purchase_time) {
		this.purchase_time = purchase_time;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}
}
