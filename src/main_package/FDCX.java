package main_package;
import java.util.ArrayList;
import java.util.List;
import java.util.Map
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
    private FBR fbr;

    public FDCX() 
    {
        users = new ArrayList<>();
        admins = new ArrayList<>();
        transactionLogs = new ArrayList<>();
        stockManager = new StockManager();
        nadra = new NADRA();
        fbr = new FBR();
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

    public void addAdmin(String name, String username, String email, String password, String CNIC ,  String phoneNumber ,  LocalDate DOB) 
    {
        Admin admin = new Admin(name, username, email, password , CNIC , phoneNumber ,DOB);
        admins.add(admin);
        dbHandler.registerAdmin(name, CNIC, DOB, email, phoneNumber);
        System.out.println("Admin added successfully: " + admin);
    }
    
    public void createUserAccount(User user , String username, String password)
    {
    	if(!user.isVerified())
    	{
    		System.out.println("USER NOT VERIFIED. VERIFY USER FIRST!");
    		return;
    	}
    	
    	dbHandler.createUserAccount(user.getCNIC(), username, password);
    	user.createAccount(username, password);
    }
    
    public void createAdminAccount(Admin admin , String username, String password)
    {
    	if(!admin.isVerified())
    	{
    		System.out.println("ADMIN NOT VERIFIED. VERIFY ADMIN FIRST!");
    		return;
    	}
    	
    	dbHandler.createAdminAccount(admin.getCNIC(), username, password);
    	admin.createAccount(username, password);
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
    
 // USE CASE : VIEW EXCHNAGE RATE 
    public Map <String,Double> viewCryptoExchangeRates()
    {
    	return cryptoService.getCryptoExchangeRates();
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
