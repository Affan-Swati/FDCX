package main_package;

public interface CryptoService 
{
	// Buy cryptocurrency
    void buyCrypto(String userID, String cryptoType, double amount);

    // Sell cryptocurrency
    boolean sellCrypto(String userID, String cryptoType, double amount);

    // Check the balance of a specific cryptocurrency
    double checkCryptoBalance(String userID, String cryptoType);

    // Transfer cryptocurrency to another wallet
    boolean transferCrypto(String fromWalletID, String toWalletID, String cryptoType, double amount);

    // View cryptocurrency transaction history
    void viewCryptoTransactionHistory(String userID);
}
