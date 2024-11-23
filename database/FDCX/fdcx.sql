CREATE DATABASE FDCX;
USE FDCX;

CREATE TABLE Users 
(
    UserID VARCHAR(15) PRIMARY KEY,    -- Unique ID CNIC for each user
	AccountID INT,  
    Name VARCHAR(100) NOT NULL,               -- User's name
    DOB DATE NOT NULL,                         -- User's date of birth
    Email VARCHAR(100) NOT NULL,              -- User's email
    PhoneNumber VARCHAR(15),                  -- User's phone number
    JoinDate DATE NOT NULL,                   -- Join date of the user
    IsVerified BOOLEAN DEFAULT FALSE,          -- Whether the user is verified or not
     FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)  -- Link to Accounts table (one-to-one relation with user account)
);

CREATE TABLE Accounts 
(
    AccountID INT AUTO_INCREMENT PRIMARY KEY,     -- Unique ID for each account
    Username VARCHAR(50) NOT NULL UNIQUE,         -- Username associated with the account
    Password VARCHAR(100) NOT NULL,               -- Password for the account
    Type ENUM('user', 'admin') NOT NULL,          -- Type of account: 'user' or 'admin'
    WalletID INT,                                 -- Foreign key to Wallet table (only for users)
    SubscriptionID INT,                           -- Foreign key to Subscription table (only for users)
    LoyaltyPoints INT DEFAULT 0,                  -- Loyalty points for the account
    TransactionHistory TEXT,                      -- History of transactions (e.g., JSON, logs)
    UserID INT,                                   -- Foreign key to Users table (for both users and admins)
    FOREIGN KEY (UserID) REFERENCES Users(UserID),  -- Link to Users table
    FOREIGN KEY (WalletID) REFERENCES Wallets(WalletID),         -- Link to Wallet table
    FOREIGN KEY (SubscriptionID) REFERENCES Subscriptions(SubscriptionID) -- Link to Subscription table
);

CREATE TABLE Admins 
(
    AdminID VARCHAR(15) PRIMARY KEY,    -- Unique ID (CNIC) for each admin
    AccountID INT,                              -- Foreign key to Accounts table
	Name VARCHAR(100) NOT NULL,               -- User's name
    DOB DATE NOT NULL,                         -- User's DOB
    Email VARCHAR(100) NOT NULL,              -- User's email
    PhoneNumber VARCHAR(15),                  -- User's phone number
    JoinDate DATE NOT NULL,                   -- Join date of the user
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID)  -- Link to Accounts table (one-to-one relation with admin account)
);

CREATE TABLE Stocks (
    StockID INT AUTO_INCREMENT PRIMARY KEY,      -- Unique ID for each stock entry
    Name VARCHAR(100) NOT NULL,                  -- Stock's name
    UnitPrice DECIMAL(10, 2) NOT NULL,           -- Stock's unit price
    PurchaseTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Time when the stock was purchased
    Available BOOLEAN DEFAULT TRUE               -- Whether the stock is available
);

CREATE TABLE Currencies 
(
    CurrencyID INT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for each currency
    CurrencyName VARCHAR(100) NOT NULL,          -- Name of the currency (e.g., US Dollar, Bitcoin)
    CurrencyCode VARCHAR(10) NOT NULL UNIQUE,    -- Code for the currency (e.g., USD, BTC)
    RateAgainstUSD DECIMAL(10, 2) NOT NULL,      -- Exchange rate compared to USD
    Type ENUM('Fiat', 'Crypto') NOT NULL,        -- Type of currency: "Fiat" or "Crypto"
    Amount DECIMAL(10, 2) DEFAULT 0              -- Amount of the currency
);

CREATE TABLE Wallets (
    WalletID VARCHAR(50) NOT NULL PRIMARY KEY,   -- Unique identifier for each wallet
    UserID INT NOT NULL,                         -- Foreign key to Users table
    FOREIGN KEY (UserID) REFERENCES Users(UserID) -- Link to Users table
);

CREATE TABLE Subscriptions 
(
    SubscriptionID INT AUTO_INCREMENT PRIMARY KEY, -- Unique ID for each subscription
    Name VARCHAR(100) NOT NULL,                    -- Subscription name (e.g., Basic, Premium)
    Type ENUM('monthly', 'quarterly', 'yearly', 'cancelled') NOT NULL, -- Type of subscription
    Price DECIMAL(10, 2) NOT NULL,                 -- Subscription price
    RenewalDate DATE NOT NULL                      -- Renewal date for the subscription
);

-- For stocks
CREATE TABLE UserStocks 
(
    UserID INT,                                   -- Foreign key to Users table
    StockID INT,                                  -- Foreign key to Stocks table
    Quantity INT DEFAULT 0,                       -- Quantity of the stock owned
    PRIMARY KEY (UserID, StockID),                -- Composite primary key
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (StockID) REFERENCES Stocks(StockID)
);

-- For currencies
CREATE TABLE UserCurrencies 
(
    UserID INT,                                   -- Foreign key to Users table
    CurrencyID INT,                               -- Foreign key to Currencies table
    Amount DECIMAL(10, 2) NOT NULL,               -- Amount of the currency owned
    PRIMARY KEY (UserID, CurrencyID),             -- Composite primary key
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (CurrencyID) REFERENCES Currencies(CurrencyID)
);

CREATE TABLE SystemStocks 
(
    StockID INT AUTO_INCREMENT PRIMARY KEY,       -- Unique ID for each stock in the system
    Name VARCHAR(100) NOT NULL,                   -- Stock's name (e.g., Apple, Tesla)
    UnitPrice DECIMAL(10, 2) NOT NULL,            -- Stock's unit price in USD
    Quantity INT DEFAULT 0,                       -- Quantity of the stock available
    Available BOOLEAN DEFAULT TRUE                -- Whether the stock is available for purchase
);


CREATE TABLE SystemCurrencies 
(
    CurrencyID INT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for each currency in the system
    CurrencyName VARCHAR(100) NOT NULL,          -- Name of the currency (e.g., USD, BTC)
    CurrencyCode VARCHAR(10) NOT NULL UNIQUE,    -- Code for the currency (e.g., USD, BTC)
    RateAgainstUSD DECIMAL(10, 2) NOT NULL,      -- Exchange rate compared to USD
    Type ENUM('Fiat', 'Crypto') NOT NULL,        -- Type of currency: "Fiat" or "Crypto"
    Available BOOLEAN DEFAULT TRUE               -- Whether the currency is available in the system
);

CREATE TABLE TransactionLogs (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,   -- Unique ID for each transaction
    UserID INT,                                     -- Foreign key to the Users table
    TransactionDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Date and time of the transaction
    Details TEXT,                                    -- Details of the transaction
    FOREIGN KEY (UserID) REFERENCES Users(UserID)   -- Link to Users table
);

