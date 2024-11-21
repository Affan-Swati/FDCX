//package main_package;
//
//import java.sql.*;
//import java.util.*;
//
//public class DBHandler 
//{
//
//    private static final String URL = "jdbc:mysql://localhost:3306/your_db_name"; // Change to your database URL
//    private static final String USER = "root"; // Your database username
//    private static final String PASSWORD = "password"; // Your database password
//    private Connection connection;
//
//    // Constructor to initialize DB connection
//    public DBHandler() 
//    {
//        try 
//        {
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//            System.out.println("Database connected successfully.");
//        } 
//        catch (SQLException e) 
//        {
//            System.out.println("Database connection failed: " + e.getMessage());
//        }
//    }
//
//    // Add a user to the database
//    public void addUser(User user)
//    {
//        String query = "INSERT INTO users (user_id, user_name, email, password) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, user.getUserID());
//            stmt.setString(2, user.getUserName());
//            stmt.setString(3, user.getEmail());
//            stmt.setString(4, user.getPassword());
//            stmt.executeUpdate();
//            System.out.println("User added successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error adding user: " + e.getMessage());
//        }
//    }
//
//    // Add an admin to the database
//    public void addAdmin(Admin admin) {
//        String query = "INSERT INTO admins (admin_id, admin_name, email, password) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, admin.getAdminID());
//            stmt.setString(2, admin.getAdminName());
//            stmt.setString(3, admin.getEmail());
//            stmt.setString(4, admin.getPassword());
//            stmt.executeUpdate();
//            System.out.println("Admin added successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error adding admin: " + e.getMessage());
//        }
//    }
//
//    // Add a stock to the database
//    public void addStock(Stock stock) {
//        String query = "INSERT INTO stocks (stock_id, stock_name, quantity_available) VALUES (?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, stock.getStockID());
//            stmt.setString(2, stock.getStockName());
//            stmt.setInt(3, stock.getQuantityAvailable());
//            stmt.executeUpdate();
//            System.out.println("Stock added successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error adding stock: " + e.getMessage());
//        }
//    }
//
//    // Log a transaction in the database
//    public void addTransactionLog(TransactionLog log) {
//        String query = "INSERT INTO transaction_logs (user_id, action) VALUES (?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setString(1, log.getUser().getUserID());
//            stmt.setString(2, log.getDetails());
//            stmt.executeUpdate();
//            System.out.println("Transaction logged successfully.");
//        } catch (SQLException e) {
//            System.out.println("Error logging transaction: " + e.getMessage());
//        }
//    }
//
//    // Get a list of users from the database
//    public List<User> getUsers() {
//        List<User> users = new ArrayList<>();
//        String query = "SELECT * FROM users";
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                String userID = rs.getString("user_id");
//                String userName = rs.getString("user_name");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                users.add(new User(userID, userName, email, password));
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving users: " + e.getMessage());
//        }
//        return users;
//    }
//
//    // Get a list of admins from the database
//    public List<Admin> getAdmins() {
//        List<Admin> admins = new ArrayList<>();
//        String query = "SELECT * FROM admins";
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                String adminID = rs.getString("admin_id");
//                String adminName = rs.getString("admin_name");
//                String email = rs.getString("email");
//                String password = rs.getString("password");
//                admins.add(new Admin(adminID, adminName, email, password));
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving admins: " + e.getMessage());
//        }
//        return admins;
//    }
//
//    // Get a list of stocks from the database
//    public List<Stock> getStocks() 
//    {
//        List<Stock> stocks = new ArrayList<>();
//        String query = "SELECT * FROM stocks";
//        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
//            while (rs.next()) {
//                String stockID = rs.getString("stock_id");
//                String stockName = rs.getString("stock_name");
//                int quantityAvailable = rs.getInt("quantity_available");
//                stocks.add(new Stock(stockID, stockName, quantityAvailable));
//            }
//        } catch (SQLException e) {
//            System.out.println("Error retrieving stocks: " + e.getMessage());
//        }
//        return stocks;
//    }
//
//    // Close the database connection
//    public void closeConnection() 
//    {
//        try 
//        {
//            if (connection != null) 
//            {
//                connection.close();
//                System.out.println("Database connection closed.");
//            }
//        } 
//        catch (SQLException e) 
//        {
//            System.out.println("Error closing connection: " + e.getMessage());
//        }
//    }
//}
//
