package main_package;

import java.sql.*;

public class FBR 
{
    private static final double DEFAULT_TAX = 7.5; // Default tax rate
    private static final String connectionString = "jdbc:mysql://localhost:3306/FBR"; // Database URL
    private static final String USER = "root"; // Database username
    private static final String PASSWORD = "Affan@2004"; // Database password

    public FBR() 
    {
        
    }

    // Record a new transaction (buy/sell)
    public void recordTransaction(String cnic, String name, String transactionType, String assetType, String assetName, 
                                  String assetCode, double quantity, double unitPrice, double taxPercentage, String remarks) 
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
            stmt.setDouble(9, taxPercentage);
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
    public void generateTransactionReport(String cnic) 
    {
        String query = "SELECT * FROM TransactionLogs WHERE CNIC = ?";
        try (Connection conn = DriverManager.getConnection(connectionString, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, cnic);
            ResultSet rs = stmt.executeQuery();

            double totalTaxCollected = 0.0;
            double totalBuyValue = 0.0;
            double totalSellValue = 0.0;
            boolean hasTransactions = false;

            System.out.println("Transaction Report for CNIC: " + cnic);
            System.out.println("--------------------------------------------------");
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

                System.out.println("Name: " + name);
                System.out.println("Date of Transaction: " + dateOfTransaction);
                System.out.println("Transaction Type: " + transactionType);
                System.out.println("Asset Type: " + assetType);
                System.out.println("Asset Name: " + assetName);
                System.out.println("Quantity: " + quantity);
                System.out.println("Unit Price: " + unitPrice);
                System.out.println("Tax Collected: " + taxCollected);
                System.out.println("Total Transaction Value: " + totalValue);
                System.out.println("--------------------------------------------------");
            }

            if (hasTransactions) {
                System.out.println("Summary:");
                System.out.println("Total Buy Value: " + totalBuyValue);
                System.out.println("Total Sell Value: " + totalSellValue);
                System.out.println("Total Tax Collected: " + totalTaxCollected);
            } else {
                System.out.println("No transactions found for CNIC: " + cnic);
            }

        } catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

	public static double getDefaultTax() 
	{
		return DEFAULT_TAX;
	}
}
