package main_package;

import java.util.*;

public class CurrencyManager 
{
    private List<Currency> currencyList; // List of currencies in the system

    // Constructor
    public CurrencyManager() {
        this.currencyList = new ArrayList<>();
    }

    // Add a new currency to the system
    public void addCurrency(Currency currency)
    {
        for (Currency c : currencyList) {
            if (c.getCurrencyCode().equals(currency.getCurrencyCode())) {
                System.out.println("Currency already exists in the system.");
                return;
            }
        }
        currencyList.add(currency);
        System.out.println("Currency added successfully: " + currency.getCurrencyCode());
    }

    // Remove a currency by code from the system
    public void removeCurrency(String currencyCode) 
    {
        for (Currency c : currencyList) {
            if (c.getCurrencyCode().equals(currencyCode)) {
                currencyList.remove(c);
                System.out.println("Currency removed successfully: " + currencyCode);
                return;
            }
        }
        System.out.println("Currency not found: " + currencyCode);
    }

    // Add currency to a user's wallet
    public void addCurrencyToWallet(Wallet wallet, String currencyCode, double amount) 
    {
        // Check if the currency exists in the system
        Currency systemCurrency = findCurrency(currencyCode);
        if (systemCurrency == null) {
            System.out.println("Currency not found in the system: " + currencyCode);
            return;
        }

        // Check if the system has enough of that currency
        if (systemCurrency.getAmount() >= amount) {
            wallet.addCurrency(currencyCode, amount); // Add to wallet
            systemCurrency.setAmount(systemCurrency.getAmount() - amount); // Decrease from system
            System.out.println(amount + " units of " + currencyCode + " added to wallet.");
        } else {
            System.out.println("Insufficient amount of " + currencyCode + " in the system.");
        }
    }

    // Remove currency from a user's wallet and add it back to the system
    public void removeCurrencyFromWallet(Wallet wallet, String currencyCode, double amount) 
    {
        // Check if the wallet has enough of the currency
        if (wallet.removeCurrency(currencyCode, amount)) {
            // Add back to the system
            Currency systemCurrency = findCurrency(currencyCode);
            if (systemCurrency != null) {
                systemCurrency.setAmount(systemCurrency.getAmount() + amount);
                System.out.println(amount + " units of " + currencyCode + " removed from wallet and added back to the system.");
            }
        } else {
            System.out.println("Insufficient balance or currency not found in wallet.");
        }
    }

    // Find a currency by its code in the system
    private Currency findCurrency(String currencyCode) 
    {
        for (Currency c : currencyList) {
            if (c.getCurrencyCode().equals(currencyCode)) {
                return c;
            }
        }
        return null; // Currency not found
    }

    // Display all currencies in the system
    public void displayCurrencies() {
        System.out.println("Currencies in the system:");
        for (Currency c : currencyList) {
            System.out.println(c);
        }
    }
}
