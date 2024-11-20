package main_package;

public class TransactionLog 
{
    private DateTime dateTime; // Object to handle date and time operations
    private User user;         // The user associated with the transaction
    private String details;    // Details of the transaction

    // Constructor
    public TransactionLog(User user, String details) 
    {
        this.dateTime = new DateTime(); // Initialize DateTime object
        this.user = user;               // Set the user associated with this log
        this.details = details;         // Set the transaction details
    }

    // Getter for Date and Time
    public String getTransactionDate() 
    {
        return DateTime.getCurrentDate(); // Use DateTime class for the current date
    }

    public String getTransactionTime() 
    {
        return DateTime.getCurrentTime(); // Use DateTime class for the current time
    }

    // Getter for user
    public User getUser() 
    {
        return user;
    }

    // Getter for transaction details
    public String getDetails() 
    {
        return details;
    }

    public String toString() 
    {
        return "TransactionLog{" +
                "date_time=" + dateTime.toString() +
                ", user=" + user.toString() +
                ", details='" + details + '\'' +
                '}';
    }
}
