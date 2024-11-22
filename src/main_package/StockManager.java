package main_package;
import java.util.ArrayList;
import java.util.List;

public class StockManager 
{
    private List<Stock> stockList;
    
    public StockManager()
    {
    	 this.stockList = new ArrayList<>();
    }
    
    public void addStock(Stock stock) 
    {
        stockList.add(stock);
    }
    
    public void removeStock(Stock stock) 
    {
        stockList.remove(stock);
    }

    public void viewStocks() 
    {
        System.out.println("Stocks in the system:");
        for (Stock stock : stockList) 
        {
            System.out.println(stock);
        }
    }
    
    public void assignStockToUser(User user, Stock stock, int quantity) 
    {
    	//TODO
    }
    
	public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}
    
}
