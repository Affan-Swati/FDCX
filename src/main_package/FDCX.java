package main_package;
import java.util.ArrayList;
import java.util.List;


public class FDCX 
{
    private List<User> users;
    private List<Admin> admins;
    private List<TransactionLog> transactionLogs;
    private StockManager stockManager;
    private CurrencyManager currencyManager;
    private BankingService bankingServices;
    private CryptoService cryptoServices;
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
    }

    // Register a new user
    public void registerUser(String name, String username, String email, String password, String CNIC ,  String phoneNumber) 
    {
        User user = new User(name,username, email ,password, CNIC ,phoneNumber);
        users.add(user);
        System.out.println("User registered successfully: " + user);
    }

    // Add a new admin
    public void addAdmin(String name, String username, String email, String password, String CNIC ,  String phoneNumber) 
    {
        Admin admin = new Admin(name, username, email, password , CNIC , phoneNumber);
        admins.add(admin);
        System.out.println("Admin added successfully: " + admin);
    }

    // Verify a user
    public void verifyUser(String username) 
    {
        // TODO via NADRA DB
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
    // List all admins
    public void listAdmins() 
    {
        System.out.println("Admins:");
        for (Admin admin : admins) 
        {
            System.out.println(admin);
        }
    }
}
