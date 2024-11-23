package main_package;
import java.util.List;
import java.util.ArrayList;

public class StockManager 
{
	private List<Stock> stocks;
    private DBHandler dbHandler;
    private CurrencyManager currencyManager;
    
    public StockManager()
    {
    	 this.stocks = new ArrayList<>();
    	 this.dbHandler = DBHandler.getInstance();
    	 this.currencyManager = CurrencyManager.getInstance();
    }
    
    public void addStockToSystem(String stockName , double unitPrice, int quantity)
    {
    	if (quantity < 0) 
        {
            System.out.println("Quantity must be greater than zero!");
            return;
        }
    	
    	int stockIndex = this.getStockIndex(stockName);
    	
    	if( stockIndex == -1) // add stock to system if not present already
    	{
    		Stock stock = new Stock (stockName,unitPrice,quantity);
        	stocks.add(stock);
    	}
    	
    	else
    	{
    		Stock stock = stocks.get(stockIndex);
    		stock.setQuantity(stock.getQuantity() + quantity);
    	}
    }
    
    public boolean removeStockFromSystem(String stockName , int quantity) // doesn't remove the entry , makes it 0 if entire stock removed
    {
    	if (quantity < 0) 
        {
            System.out.println("Quantity must be greater than zero!");
            return false;
        }
    	
    	int stockIndex = this.getStockIndex(stockName);

        if (stockIndex == -1) 
        {
            System.out.println("Stock not found in system: " + stockName);
            return false;
        }
        
        Stock stock = stocks.get(stockIndex);
        
        int currentBalance =  stock.getQuantity();
        if (currentBalance >= quantity) 
        {
        	stock.setQuantity(currentBalance - quantity);
            System.out.println(quantity + " units of " + stockName + " removed from account.");
            return true;
        } else
        {
            System.out.println("Insufficient balance of " + stockName + " in account.");
            return false;
        }
    }
    
    private int getStockIndex(String stockName)
    {
    	int index = 0;
    	
    	for(Stock stock : stocks)
    	{
    		if(stock.getName().equals(stockName))
    		{
    			return index;
    		}
    		index++;
    	}
    	
    	index = -1; // not found
    	return index;
    }
    
    public boolean addStockToUser(User user , Stock stock , int quantity)
    {
    	if(!currencyManager.hasEnoughUSD(user, stock.getUnitPrice() ,quantity))
    	{
    		System.out.println("User doesn't have enough USD to Buy Stock!");
    		return false;
    	}
    	
    	if(this.removeStockFromSystem(stock.getName(), quantity))
    	{
    		user.getAccount().addStock(stock, quantity);
        	dbHandler.addUserStock(user.getCNIC(),stock.getName() , quantity);
        	currencyManager.removeCurrencyFromWallet(user.getAccount().getWallet(), "USD", stock.getUnitPrice() * quantity);
        	dbHandler.updateUserBalance(user.getCNIC(), "USD", stock.getUnitPrice() * quantity, false);
        	currencyManager.addCurrency("Dollar", "USD", 1.0 , "fiat", stock.getUnitPrice() * quantity);
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
    		this.addStockToSystem(stock.getName(), stock.getUnitPrice(), quantity);
        	dbHandler.removeUserStock(user.getCNIC(),stock.getName() , quantity);
        	currencyManager.addCurrencyToWallet(user.getAccount().getWallet(), "USD", stock.getUnitPrice() * quantity);
        	dbHandler.updateUserBalance(user.getCNIC(), "USD", stock.getUnitPrice() * quantity, true);
        	currencyManager.removeCurrency("USD", stock.getUnitPrice() * quantity );
        	return true;
    	}
    	
    	else
    	{
    		System.out.println("Not enough stocks in user: " + user.getCNIC() + " account!");
    		return false;
    	}
    }
    
    public List<Stock> getStocks() 
    {
        return stocks;
    }

    
}
