package main_package;

public class Admin extends User
{
    private FraudMonitor fraudMonitor;
	
	 public Admin(String name ,String username, String email, String password, String CNIC, String phoneNumber) 
	 {
		 super(name ,username, email, password, CNIC, phoneNumber);
	 }
	 
	 void resolveAnamoly(Anamoly anamoly)
	 {
		 anamoly.assignAdmin(this);
		 anamoly.resolve();
	 }
}
