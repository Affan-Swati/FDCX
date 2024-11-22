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

    // 5. Retrieve exchange rates
    public Map<String, Double> getExchangeRates() 
    {
        String query = "SELECT CurrencyCode, RateAgainstUSD FROM SystemCurrencies WHERE Available = TRUE";
        Map<String, Double> exchangeRates = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                exchangeRates.put(rs.getString("CurrencyCode"), rs.getDouble("RateAgainstUSD"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exchangeRates;
    }

    // 6. Deposit funds into the user's wallet
    public boolean depositFunds(String walletID, String currencyCode, double amount) 
    {
        String query = "INSERT INTO WalletCurrencyBalances (WalletID, CurrencyCode, Balance) " +
                       "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE Balance = Balance + ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, walletID);
            stmt.setString(2, currencyCode);
            stmt.setDouble(3, amount);
            stmt.setDouble(4, amount);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 7. Withdraw funds from the user's wallet
    public boolean withdrawFunds(String walletID, String currencyCode, double amount) 
    {
        String query = "UPDATE WalletCurrencyBalances SET Balance = Balance - ? " +
                       "WHERE WalletID = ? AND CurrencyCode = ? AND Balance >= ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, amount);
            stmt.setString(2, walletID);
            stmt.setString(3, currencyCode);
            stmt.setDouble(4, amount);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
