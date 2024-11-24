package main_package;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.time.*;


public class FDCX 
{
    private List<User> users;
    private List<Admin> admins;
    private List<TransactionLog> transactionLogs;
    private DBHandler dbHandler;
    private StockManager stockManager;
    private BankingService bankingService;
    private CryptoService cryptoService;
    private NADRA nadra; 

    public FDCX() 
    {
        users = new ArrayList<>();
        admins = new ArrayList<>();
        transactionLogs = new ArrayList<>();
        stockManager = StockManager.getInstance();
        nadra = new NADRA();
        dbHandler = DBHandler.getInstance();
        bankingService = new BankingService();
        cryptoService = new CryptoService();
    }

    // USE CASE : REGISTER USER
    public void registerUser(String name,String email,String CNIC,String phoneNumber , LocalDate DOB) 
    {
        User user = new User(name,email,CNIC ,phoneNumber,DOB);
        users.add(user);
        dbHandler.registerUser(name, CNIC, DOB, email, phoneNumber);
        System.out.println("User registered successfully: " + user);
    }

    public void addAdmin(String name, String email, String CNIC ,  String phoneNumber ,  LocalDate DOB , String username , String password) 
    {
        Admin admin = new Admin(name, email, CNIC , phoneNumber ,DOB);
        admins.add(admin);
        
        dbHandler.registerAdmin(name, CNIC, DOB, email, phoneNumber);
        this.createAdminAccount(admin, username,password);
        System.out.println("Admin added successfully: " + admin);
    }
    
    public void createUserAccount(User user , String username, String password)
    {
    	if(!user.isVerified())
    	{
    		System.out.println("USER NOT VERIFIED. VERIFY USER FIRST!");
    		return;
    	}
    	
    	user.createAccount(username, password);
    	dbHandler.createUserAccount(user.getCNIC(), username, password,user.getAccount().getWallet().getWalletID());   	
    }
    
    private void createAdminAccount(Admin admin , String username, String password)
    {
    	dbHandler.createAdminAccount(admin.getCNIC(), username, password);
    	admin.registerAccount(username, password);
    }
    
    

    public boolean verifyUser(User user) 
    {
        if(nadra.verifyUser(user.getCNIC(), user.getName()))
        {
        	user.setVerified(true);
        	return true;
        }   		
        else
        {
        	return false;
        }
    }
    
    // USE CASE : VIEW EXCHNAGE RATE 
    public Map <String,Double> viewFiatExchangeRates()
    {
    	return bankingService.getFiatExchangeRates();
    }
    
 // USE CASE : VIEW EXCHANGE RATE 
    public Map <String,Double> viewCryptoExchangeRates()
    {
    	return cryptoService.getCryptoExchangeRates();
    }
 
    public boolean withdrawFunds(String type , User user , String currencyCode , double amount) // type fiat or crypto
    {
    	
    	if("fiat".equals(type))
    	{
    		bankingService.sellFiat(user, currencyCode, amount);
    		this.logTransaction(user, currencyCode, amount, type + " sold");
    		return true;
    	}
    	else if ("crypto".equals(type))
    	{
    		cryptoService.sellCrypto(user, currencyCode, amount);
    		this.logTransaction(user, currencyCode, amount, type + " sold");
    		return true;
    	}
    	return false;
    	
    }
    
    public boolean depositFunds(String type , User user , String currencyCode , double amount) // type fiat or crypto
    {
    	if("Fiat".equals(type))
    	{
    		bankingService.buyFiat(user, currencyCode, amount);
    		this.logTransaction(user, currencyCode, amount, type + " bought");
    		user.getAccount().setLoyaltyPoints(user.getAccount().getLoyaltyPoints() + 10);
    		return true;
    	}
    	else if ("Crypto".equals(type))
    	{
    		cryptoService.buyCrypto(user, currencyCode, amount);
    		this.logTransaction(user, currencyCode, amount, type + " bought");
    		user.getAccount().setLoyaltyPoints(user.getAccount().getLoyaltyPoints() + 10);
    		return true;
    	}
    	
    	return false;
    	
    }
    
    public boolean tradeFunds(String type , User fromUser , User toUser, String currencyCode , double amount) // type fiat or crypto
    {
    	if("Fiat".equals(type))
    	{
    		bankingService.transferFiat(fromUser, toUser, currencyCode, amount);
    		this.logTransaction(fromUser, currencyCode, amount, type + " trade out");
    		this.logTransaction(toUser, currencyCode, amount, type + " trade in");
    		return true;
    	}
    	else if ("Crypto".equals(type))
    	{
    		cryptoService.transferCrypto(fromUser, toUser, currencyCode, amount);
    		this.logTransaction(fromUser, currencyCode, amount, type + " trade out");
    		this.logTransaction(toUser, currencyCode, amount, type + " trade in");
    		return true;
    	}
    	return false;
    	
    }
    
    public void exchangeFiat(String fromCurrencyCode , String toCurrencyCode , User user , double amount) // exchange is free of cost too
    {
    	bankingService.exchangeFiat(fromCurrencyCode, toCurrencyCode, user, amount);
    }
    
    public void exchangeCrypto(String fromCryptoCode , String toCryptoCode , User user , double amount) // exchange is free of cost too
    {
    	cryptoService.exchangeCrypto(fromCryptoCode, toCryptoCode, user, amount);
    }
    
    public void assignStockToUser(User user , Stock stock , int quantity)
    {
    	stockManager.addStockToUser(user, stock, quantity);
    	user.getAccount().setLoyaltyPoints(user.getAccount().getLoyaltyPoints() + 10);
    	this.logTransaction(user, stock.getName(), quantity, " stock bought");
    }
    
    public void removeStockFromUser(User user , Stock stock , int quantity)
    {
    	stockManager.removeStockFromUser(user, stock, quantity);
    	this.logTransaction(user, stock.getName(), quantity, " stock sold");
    }

    public boolean claimLoyaltyPoints(User user)
    {
    	int points = user.getAccount().getLoyaltyPoints();
    	
    	if(points == 0)
    	{
    		System.out.println("User has no loyalty points!");
    		return false;
    	}
    	
    	
    	user.getAccount().setLoyaltyPoints(0);
    
    	dbHandler.updateUserBalance(user.getCNIC(), "Dollar" ,"USD", 1.0 ,points/10 , "Fiat" , true);
    	dbHandler.updateSystemBalance( "Dollar" ,"USD", 1.0 ,points/10 , "Fiat" , false);
    	
    	return true;
 
    }
    
    public void addCurrencyToSystem(String currencyName, String currencyCode, double rateAgainstUSD, String type, double amount)
    {
    	this.admins.getFirst().addCurrencyToSystem(currencyName, currencyCode, rateAgainstUSD, type, amount);
    }
    
    public void removeCurrencyFromSystem(String currencyCode, double amount)
    {
    	this.admins.getFirst().removeCurrencyFromSystem(currencyCode, amount);
    }
    
    public void addStockToSystem(String stockName , double unitPrice , int quantity)
    {
    	this.admins.getFirst().addStockToSystem(stockName, unitPrice, quantity);
    }
    
    public void removeStockFromSystem(String stockName , int quantity)
    {
    	this.admins.getFirst().removeStockFromSystem(stockName, quantity);
    }
    
    
    public void logTransaction( User user , String currencyCode , double amount ,String type)
    {
    	TransactionLog log = new TransactionLog(user , currencyCode + " " + amount + " " + type);
    	transactionLogs.add(log);
    	dbHandler.recordTransaction(user.getCNIC(), currencyCode, amount, type);
    }
    
    public List<String> getTransactionHistory(String userId)
    {
    	return dbHandler.getTransactionHistory(userId);
    }
    
    public Pair<Map<String,Integer>, Map<String , Double>> getWallet(String userId)
    {
    	if(!isUser(userId))
    	{
    		System.out.println("User doesn't exist in the system!");
    		return null;
    	}
    	
    	User user = getUser(userId);
    	
    	return new Pair<Map<String,Integer>,Map<String,Double>>(user.getAccount().getStockBalances() ,user.getAccount().getWallet().getCurrencyBalances());
    }
    
    public boolean predictStockTrend(String stockName)
    {
    	return stockManager.predictStockTrend(stockName);
    }
    
    
    private boolean isUser(String userId)
    {
    	for(User user : users)
    	{
    		if(user.getCNIC().equals(userId))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public User getUser(String userId)
    {
    	for(User user : users)
    	{
    		if(user.getCNIC().equals(userId))
    		{
    			return user;
    		}
    	}
    	
    	return null;
    }
    
    public Admin getAdmin(String adminId)
    {
    	for(Admin admin : admins)
    	{
    		if(admin.getCNIC().equals(adminId))
    		{
    			return admin;
    		}
    	}
    	
    	return null;
    }
   
    // List all users
    public void listUsers() 
    {
        System.out.println("Registered Users:");
        for (User user : users) 
        {
            System.out.println(user);
        }
    }
    
    public void listAdmins() 
    {
        System.out.println("Admins:");
        for (Admin admin : admins) 
        {
            System.out.println(admin);
        }
    }
}
