package main_package;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.*;

public class UnitTesting 
{	
//	@Test
//    public void createUser() 
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.registerUser("Affan Ahmad", "affanswati12@gmail.com", "3740583626159", "+923339464521", LocalDate.of(2004, 04, 12));
//		
//		User user = fdcx.getUser("3740583626159");
//		assertEquals(user.getCNIC(),"3740583626159");		
//    }
	
//	@Test
//	public void verifyUserViaNadra()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.registerUser("Affan Ahmad", "affanswati12@gmail.com", "3740583626159", "+923339464521", LocalDate.of(2004, 04, 12));
//		User user = fdcx.getUser("3740583626159");
//		assertTrue(fdcx.verifyUser(user));
//	}
	
//	@Test
//	public void createUserAccount()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.registerUser("Affan Ahmad", "affanswati12@gmail.com", "3740583626159", "+923339464521", LocalDate.of(2004, 04, 12));
//		User user = fdcx.getUser("3740583626159");
//		fdcx.verifyUser(user);
//		
//		fdcx.createUserAccount(user,"affan","123");
//	}
	
//	@Test
//	public void createAdminAndAdminAccount()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.addAdmin("Adil Nadeem", "adil.nadeem@gmail.com", "1234567898765", "+923339412345", LocalDate.of(2002, 9, 27),"adil","123");
//		
//	}
//	
	
//	@Test
//	public void addStockToSystem()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.addAdmin("Asim Muneer", "asim.muneer@gmail.com", "1234512398765", "+923339812345", LocalDate.of(1947, 8, 14),"asim","123");
//		fdcx.addStockToSystem("Tesla", 20.0, 20);
//	}
	
//	@Test
//	public void removeStockFromSystem()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.addAdmin("Azlan Awan", "azlan.awan@gmail.com", "1234512398987", "+923335672345", LocalDate.of(2023, 04, 27),"azlan","123");
//		fdcx.addStockToSystem("SpaceX", 32.0, 50);
//		fdcx.removeStockFromSystem("SpaceX", 45);
//	}
	
//	@Test
//	public void addRemoveCurrencyFromToSystem()
//	{
//		FDCX fdcx = new FDCX();
//		fdcx.addAdmin("Azlan Awan", "azlan.awan@gmail.com", "1234512398987", "+923335672345", LocalDate.of(2023, 04, 27),"azlan","123");
//		fdcx.addCurrencyToSystem("dollar", "USD" , 1.0, "Fiat", 2000);
//		fdcx.addCurrencyToSystem("pound", "GBP" , 0.80, "Fiat", 1200);
//		fdcx.removeCurrencyFromSystem("GBP", 100);
//		fdcx.addCurrencyToSystem("bitcoin", "BTC" , 100.50, "Crypto", 18);
//		fdcx.removeCurrencyFromSystem("BTC", 5);
//	}
	
	@Test
	public void assignStockToUser()
	{
		FDCX fdcx = new FDCX();
		fdcx.addAdmin("Azlan Awan", "azlan.awan@gmail.com", "1234512398987", "+923335672345", LocalDate.of(2023, 04, 27),"azlan","123");
		fdcx.registerUser("Affan Ahmad", "affanswati12@gmail.com", "3740583626159", "+923339464521", LocalDate.of(2004, 04, 12));
		User user = fdcx.getUser("3740583626159");
		fdcx.verifyUser(user);
		fdcx.createUserAccount(user,"affan","123");
		
		fdcx.addStockToSystem("SpaceX", 10.0, 50);
		
		fdcx.addCurrencyToSystem("Dollar", "USD", 1.0, "Fiat", 20000);
		fdcx.addCurrencyToSystem("Pound", "GBP", 0.85, "Fiat", 10000);
		DBHandler.getInstance().updateUserBalance(user.getCNIC(), "Dollar", "USD", 1.0, 200000, "Fiat", true);
		user.getAccount().getWallet().addCurrency("USD", 200000);
		
		
		Stock stock = new Stock("SpaceX" , 10.0 , 0);
		fdcx.assignStockToUser(user, stock, 7);
		//fdcx.removeStockFromUser(user, stock, 5);
	}
	
	
}
