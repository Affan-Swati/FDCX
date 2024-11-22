package main_package;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DBHandler // singleton 
{
    private static final String DB_URL = "jdbc:mysql://localhost:3306/FDCX";
    private static final String USER = "root";
    private static final String PASSWORD = "Affan@2004";
    private static DBHandler instance = null;

    private DBHandler() 
    {
  
    }
    
    public static DBHandler getInstance()
    {
    	if(instance == null)
    	{
    		instance = new DBHandler();
    	}
    	return instance;
    }

    // 1. Register a new user (without account creation)
    public boolean registerUser(String name, String cnic, LocalDate dob, String email, String phoneNumber) 
    {
        String insertUser = "INSERT INTO Users (Name, CNIC, DOB, Email, PhoneNumber, JoinDate, IsVerified) VALUES (?, ?, ?, ?, ?, CURDATE(), FALSE)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, cnic);
            stmt.setDate(3, java.sql.Date.valueOf(dob));  // Convert LocalDate to sql.Date
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return true; // User successfully registered
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public void setUserVerification(String CNIC, boolean status) 
    {
    	  String updateVerification = "UPDATE Users SET IsVerified = ? WHERE CNIC = ?";
    	  try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    	       PreparedStatement stmt = conn.prepareStatement(updateVerification)) 
    	  {
    	    stmt.setBoolean(1, status);
    	    stmt.setString(2, CNIC);
    	    stmt.executeUpdate();
    	  } catch (SQLException e) {
    	    e.printStackTrace();
    	  }
    }
    

    // 2. Register an admin (without account creation)
    public boolean registerAdmin(String name, String cnic, LocalDate dob, String email, String phoneNumber) {
        String insertAdmin = "INSERT INTO Users (Name, CNIC, DOB, Email, PhoneNumber, JoinDate, IsVerified) VALUES (?, ?, ?, ?, ?, CURDATE(), FALSE)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertAdmin, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, cnic);
            stmt.setDate(3, java.sql.Date.valueOf(dob));
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return true; // Admin successfully registered
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 3. Create an account for a user
    public boolean createUserAccount(String userID, String username, String password) {
        String insertAccount = "INSERT INTO Accounts (Username, Password, Type, UserID) VALUES (?, ?, 'user', ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertAccount)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, userID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Create an account for an admin
    public boolean createAdminAccount(String adminID, String username, String password) {
        String insertAccount = "INSERT INTO Accounts (Username, Password, Type, UserID) VALUES (?, ?, 'admin', ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertAccount)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, adminID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

 
    public Map<String, Double> getFiatExchangeRates() 
    {
    	  String query = "SELECT CurrencyCode, RateAgainstUSD FROM SystemCurrencies WHERE Available = TRUE AND Type = ?";
    	  Map<String, Double> exchangeRates = new HashMap<>();
    	  try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    	       PreparedStatement stmt = conn.prepareStatement(query)) {
    	    stmt.setString(1, "fiat"); // Set the type filter to "fiat"
    	    ResultSet rs = stmt.executeQuery();

    	    while (rs.next()) {
    	      exchangeRates.put(rs.getString("CurrencyCode"), rs.getDouble("RateAgainstUSD"));
    	    }
    	  } catch (SQLException e) {
    	    e.printStackTrace();
    	  }
    	  return exchangeRates;
    	}
    
    public Map<String, Double> getCryptoExchangeRates() 
    {
    	  String query = "SELECT CurrencyCode, RateAgainstUSD FROM SystemCurrencies WHERE Available = TRUE AND Type = ?";
	  	  Map<String, Double> exchangeRates = new HashMap<>();
	  	  try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
	  	       PreparedStatement stmt = conn.prepareStatement(query)) {
	  	    stmt.setString(1, "crypto");
	  	    ResultSet rs = stmt.executeQuery();
	
	  	    while (rs.next()) 
	  	    {
	  	      exchangeRates.put(rs.getString("CurrencyCode"), rs.getDouble("RateAgainstUSD"));
	  	    }
	  	  } catch (SQLException e) {
	  	    e.printStackTrace();
	  	  }
	  	  return exchangeRates;
  	}

    
    public boolean isSufficientSystemBalance(String currencyCode, double amount)
    {
        String query = "SELECT Amount FROM SystemCurrencies WHERE CurrencyCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,currencyCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double systemBalance = rs.getDouble("Amount");
                return systemBalance >= amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUserBalance(String userId, String currencyCode, double amount, boolean isIncrease) 
    {
        String updateQuery = "UPDATE UserCurrencies SET Amount = Amount " + (isIncrease ? "+" : "-") + " ? WHERE UserID = ? AND CurrencyCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, userId);
            stmt.setString(3, currencyCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSystemBalance(String currencyCode, double amount, boolean isIncrease) 
    {
        String updateQuery = "UPDATE SystemCurrencies SET Amount = Amount " + (isIncrease ? "+" : "-") + " ? WHERE CurrencyCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setDouble(1, amount);
            stmt.setString(2, currencyCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recordTransaction(String userId, String currencyCode, double amount, String type) 
    {
        String insertQuery = "INSERT INTO TransactionLogs (UserID, CurrencyCode, Amount, Type, TransactionDateTime) VALUES (?, ?, ?, ?, NOW())";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, userId);
            stmt.setString(2, currencyCode);
            stmt.setDouble(3, amount);
            stmt.setString(4, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean buyCrypto(String userId, String cryptoCode, double amount) {
        if (isSufficientSystemBalance(cryptoCode, amount)) {
            updateUserBalance(userId, cryptoCode, amount, true);
            updateSystemBalance(cryptoCode, amount, false);
            recordTransaction(userId, cryptoCode, amount, "Buy Crypto");
            return true;
        } else {
            return false; // Or throw an exception
        }
    }

    public boolean sellCrypto(String userId, String cryptoCode, double amount) {
        // Check user's balance
        if (isUserBalanceSufficient(userId, cryptoCode, amount)) 
        {
            if (isSufficientSystemBalance(cryptoCode, amount)) {
                updateUserBalance(userId, cryptoCode, amount, false);
                updateSystemBalance(cryptoCode, amount, true);
                recordTransaction(userId, cryptoCode, amount, "Sell Crypto");
                return true;
            } else {
                return false; // Or throw an exception
            }
        } else {
            return false; // Or throw an exception
        }
    }

    public boolean buyFiat(String userId, String currencyCode, double amount) 
    {
    	if (isSufficientSystemBalance(currencyCode, amount)) {
            updateUserBalance(userId, currencyCode, amount, true);
            updateSystemBalance(currencyCode, amount, false);
            recordTransaction(userId, currencyCode, amount, "Buy Fiat");
            return true;
        } else {
            return false; // Or throw an exception
        }
    }

    public boolean sellFiat(String userId, String currencyCode, double amount) 
    {
        if (isUserBalanceSufficient(userId, currencyCode, amount)) 
        {
            updateUserBalance(userId, currencyCode, amount, false);
            updateSystemBalance(currencyCode, amount, true);
            recordTransaction(userId, currencyCode, amount, "Sell Fiat");
            return true;
        } else 
        {
            return false; // Or throw an exception
        }
    }


    public boolean isUserBalanceSufficient(String userId, String currencyCode, double amount) 
    {
        String query = "SELECT Amount FROM UserCurrencies WHERE UserID = ? AND CurrencyCode = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,userId);
            stmt.setString(2, currencyCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double userBalance = rs.getDouble("Amount");
                return userBalance >= amount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
