package main_package;

import java.util.Map;

public class CryptoService 
{

	DBHandler dbHandler = null;
	CurrencyManager currencyManager = null;
	
	public CryptoService()
	{
		dbHandler = DBHandler.getInstance();
		currencyManager = CurrencyManager.getInstance();
	}
	
	public Map<String, Double> getCryptoExchangeRates() 
	{ 
	    return dbHandler.getCryptoExchangeRates();
	}
	
	public boolean buyCrypto(User user, String cryptoCode ,double amount)
	 {
		 if(dbHandler.buyCrypto(user.getCNIC(), cryptoCode, amount))
		 {
			 currencyManager.addCurrencyToWallet(user.getAccount().getWallet(), cryptoCode, amount);
			 return true;
		 }
		 else
		 {
			 System.out.println("INSUFFICIENT SYSTEM BALANCE!");
			 return false;
		 }
	 }
	
	public boolean sellCrypto(User user, String cryptoCode, double amount)
	{
		 if(dbHandler.sellCrypto(user.getCNIC(), cryptoCode, amount))
		 {
			 currencyManager.removeCurrencyFromWallet(user.getAccount().getWallet(), cryptoCode, amount);
			 return true;
		 }
		 else
		 {
			 System.out.println("INSUFFICIENT USER BALANCE!");
			 return false;
		 }
			 
	}

	public boolean transferCrypto(User fromUser, User toUser, String cryptoCode,double amount)
	{
		Wallet fromWallet =  fromUser.getAccount().getWallet() ;
		Wallet toWallet = toUser.getAccount().getWallet();
		
		if(fromWallet.getCurrencyBalance(cryptoCode) < amount)
		{
			System.out.println("INSUFFICIENT BALANCE!");
			return false;
		}
		
		else
		{
			fromWallet.removeCurrency(cryptoCode, amount);
			toWallet.addCurrency(cryptoCode, amount);
			
			dbHandler.updateUserBalance(fromUser.getCNIC(), cryptoCode, amount, false);
			dbHandler.updateUserBalance(toUser.getCNIC(), cryptoCode, amount, true);
			
			return true;
		}
	}

}

