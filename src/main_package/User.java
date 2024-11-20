package main_package;
import java.util.*;

class User 
{
    private String name;
    private String CNIC;
    private int age;
    private String username;
    private String email;
    private String password;
    private String phoneNumber;
    private String joinDate;
    private boolean isVerified;
    private Account account;
    
    public User(String name ,String username, String email, String password, String CNIC, String phoneNumber) 
    {
    	this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.CNIC = CNIC;
        this.phoneNumber = phoneNumber;
        this.account = new Account();
    }
    
    public String getName()
    {
    	return this.username;
    }
}
