package main_package;

import java.util.Map;

public class BankingService 
{

	private DBHandler dbHandler = null;
	private CurrencyManager currencyManager = null;
	
	public BankingService ()
	{
		dbHandler = DBHandler.getInstance();
		currencyManager = CurrencyManager.getInstance();
	}
	
	public Map<String, Double> getFiatExchangeRates() 
	{ 
	    return dbHandler.getFiatExchangeRates();
	}
	
	public boolean buyFiat(User user, String currencyCode ,double amount)
	 {
		 if(dbHandler.buyFiat(user.getCNIC(), currencyCode, amount))
		 {
			 currencyManager.addCurrencyToWallet(user.getAccount().getWallet(), currencyCode, amount);
			 return true;
		 }
		 else
		 {
			 System.out.println("INSUFFICIENT SYSTEM BALANCE!");
			 return false;
		 }
	 }
	
	public boolean sellFiat(User user, String currencyCode, double amount)
	{
		 if(dbHandler.sellFiat(user.getCNIC(), currencyCode, amount))
		 {
			 currencyManager.removeCurrencyFromWallet(user.getAccount().getWallet(), currencyCode, amount);
			 return true;
		 }
		 else
		 {
			 System.out.println("INSUFFICIENT USER BALANCE!");
			 return false;
		 }
			 
	}
	
	public boolean transferFiat(User fromUser, User toUser, String currencyCode,double amount)
	{
		Wallet fromWallet =  fromUser.getAccount().getWallet() ;
		Wallet toWallet = toUser.getAccount().getWallet();
		
		if(fromWallet.getCurrencyBalance(currencyCode) < amount)
		{
			System.out.println("INSUFFICIENT BALANCE!");
			return false;
		}
		
		else
		{
			fromWallet.removeCurrency(currencyCode, amount);
			toWallet.addCurrency(currencyCode, amount);
			
			dbHandler.updateUserBalance(fromUser.getCNIC(), currencyCode, amount, false);
			dbHandler.updateUserBalance(toUser.getCNIC(), currencyCode, amount, true);
			
			return true;
		}
	 }
	
    public void exchangeFiat(String fromCurrencyCode , String toCurrencyCode , User user , double amount)
    {
    	double fromRate = currencyManager.getCurrencyRate(fromCurrencyCode);
    	double toRate = currencyManager.getCurrencyRate(toCurrencyCode);
    	
    	double newAmount = currencyManager.convertCurrency(amount, fromRate, toRate);
    	
    	this.sellFiat(user, fromCurrencyCode, amount);
    	this.buyFiat(user, toCurrencyCode, newAmount);
    }
}

