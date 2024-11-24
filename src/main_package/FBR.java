package main_package;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;


// TODO: record transactions in FBr database

public class FBR 
{
    private static final double DEFAULT_TAX = 0.075; // Default tax rate
    private static final String connectionString = "jdbc:mysql://localhost:3306/FBR"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "Affan@2004"; // Database password

    public FBR() 
    {
    
    }
    
   
    public double calculateTax(double amount)
    {
    	return (DEFAULT_TAX * amount);
    }
    
    // Record a new transaction (buy/sell)
    public void recordTransaction(String cnic, String name, String transactionType, String assetType, String assetName, 
                                  String assetCode, double quantity, double unitPrice,String remarks) 
    {
        String query = "INSERT INTO TransactionLogs (CNIC, Name, DateOfTransaction, TransactionType, AssetType, AssetName, AssetCode, Quantity, UnitPrice, TaxPercentage, Remarks) " +
                       "VALUES (?, ?, CURDATE(), ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(connectionString, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cnic);
            stmt.setString(2, name);
            stmt.setString(3, transactionType); // Buy or Sell
            stmt.setString(4, assetType);       // Stock or Currency
            stmt.setString(5, assetName);
            stmt.setString(6, assetCode);
            stmt.setDouble(7, quantity);
            stmt.setDouble(8, unitPrice);
            stmt.setDouble(9, DEFAULT_TAX);
            stmt.setString(10, remarks);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transaction successfully recorded for CNIC: " + cnic);
            } else {
                System.out.println("Failed to record transaction for CNIC: " + cnic);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Generate a transaction report for a user
    public static List<String> generateTaxReport(String cnic) 
    {
        String query = "SELECT * FROM TransactionLogs WHERE CNIC = ?";
        List<String> report = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(connectionString, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cnic);
            ResultSet rs = stmt.executeQuery();

            double totalTaxCollected = 0.0;
            double totalBuyValue = 0.0;
            double totalSellValue = 0.0;
            boolean hasTransactions = false;

            report.add("Transaction Report for CNIC: " + cnic);
            report.add("--------------------------------------------------");

            while (rs.next()) {
                hasTransactions = true;
                String name = rs.getString("Name");
                Date dateOfTransaction = rs.getDate("DateOfTransaction");
                String transactionType = rs.getString("TransactionType");
                String assetType = rs.getString("AssetType");
                String assetName = rs.getString("AssetName");
                double quantity = rs.getDouble("Quantity");
                double unitPrice = rs.getDouble("UnitPrice");
                double taxCollected = rs.getDouble("TaxCollected");
                double totalValue = rs.getDouble("TotalValue");

                if ("Buy".equals(transactionType)) {
                    totalBuyValue += totalValue;
                } else if ("Sell".equals(transactionType)) {
                    totalSellValue += totalValue;
                }
                totalTaxCollected += taxCollected;

                report.add("Name: " + name);
                report.add("Date of Transaction: " + dateOfTransaction);
                report.add("Transaction Type: " + transactionType);
                report.add("Asset Type: " + assetType);
                report.add("Asset Name: " + assetName);
                report.add("Quantity: " + quantity);
                report.add("Unit Price: " + unitPrice);
                report.add("Tax Collected: " + taxCollected);
                report.add("Total Transaction Value: " + (totalValue + taxCollected));
                report.add("--------------------------------------------------");
            }

            if (hasTransactions) {
                report.add("Summary:");
                report.add("Total Buy Value: " + totalBuyValue);
                report.add("Total Sell Value: " + totalSellValue);
                report.add("Total Tax Collected: " + totalTaxCollected);
            } else {
                report.add("No transactions found for CNIC: " + cnic);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            report.add("Error retrieving transaction report for CNIC: " + cnic);
        }

        return report;
    }

	public static double getDefaultTax() 
	{
		return DEFAULT_TAX;
	}
}
