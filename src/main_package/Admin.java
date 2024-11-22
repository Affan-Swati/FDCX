package main_package;
import java.time.*;

public class Admin extends User
{
    private FraudMonitor fraudMonitor;
	
	 public Admin(String name ,String username, String email, String password, String CNIC, String phoneNumber , LocalDate DOB) 
	 {
		 super(name ,email,CNIC,phoneNumber,DOB);
	 }
	 
	 public void registerAccount(String username , String password)
	 {
		 this.setAccount(new Account(username,password,"admin"));
	 }
	 
	 void resolveAnamoly(Anamoly anamoly)
	 {
		 anamoly.assignAdmin(this);
		 anamoly.resolve();
	 }

	public FraudMonitor getFraudMonitor() {
		return fraudMonitor;
	}

	public void setFraudMonitor(FraudMonitor fraudMonitor) {
		this.fraudMonitor = fraudMonitor;
	}
}
