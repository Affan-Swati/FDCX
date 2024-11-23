package main_package;
import java.util.Map;
import java.util.HashMap;

public class StockManager 
{
	private Map<String, Double> stockBalances;
    private DBHandler dbHandler;
    
    public StockManager()
    {
    	 this.stockBalances = new HashMap<>();
    	 this.dbHandler = DBHandler.getInstance();
    }
    
    public void addStockToSystem(Stock stock , double quantity)
    {
    	if (quantity < 0) 
        {
            System.out.println("Quantity must be greater than zero!");
            return;
        }
    	
    	stockBalances.put(stock.getName(), stockBalances.getOrDefault(stock.getName(), 0.0) + quantity);
    }
    
    public boolean removeStockFromSystem(Stock stock , double quantity)
    {
    	if (quantity < 0) 
        {
            System.out.println("Quantity must be greater than zero!");
            return false;
        }

        if (!stockBalances.containsKey(stock.getName())) 
        {
            System.out.println("Stock not found in system: " + stock.getName());
            return false;
        }

        double currentBalance = stockBalances.get(stock.getName());
        if (currentBalance >= quantity) 
        {
        	stockBalances.put(stock.getName(), currentBalance - quantity);
            System.out.println(quantity + " units of " + stock.getName() + " removed from account.");
            return true;
        } else {
            System.out.println("Insufficient balance of " + stock.getName() + " in account.");
            return false;
        }
    }
    
    public boolean addStockToUser(User user , Stock stock , int quantity)
    {
    	if(this.removeStockFromSystem(stock, quantity))
    	{
    		user.getAccount().addStock(stock, quantity);
        	dbHandler.addUserStock(user.getCNIC(),stock.getName() , quantity);
        	return true;
    	}
    	else
    	{
    		System.out.println("Not enough stocks in system!");
    		return false;
    	}
    }
    
    public boolean removeStockFromUser(User user , Stock stock , int quantity)
    {
    	
    	if(user.getAccount().removeStock(stock, quantity))
    	{
    		this.addStockToSystem(stock, quantity);
        	dbHandler.removeUserStock(user.getCNIC(),stock.getName() , quantity);
        	return true;
    	}
    	
    	else
    	{
    		System.out.println("Not enough stocks in user: " + user.getCNIC() + " account!");
    		return false;
    	}
    }
    
}
