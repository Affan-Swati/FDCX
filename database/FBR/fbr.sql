-- Step 1: Create the FBR database
CREATE DATABASE FBR;

-- Step 2: Use the FBR database
USE FBR;

-- Step 3: Create the CitizenPurchases table
CREATE TABLE TransactionLogs 
(
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,   -- Unique identifier for each transaction
    CNIC VARCHAR(15) NOT NULL,                     -- Citizen's CNIC
    Name VARCHAR(100) NOT NULL,                    -- Citizen's name
    DateOfTransaction DATE NOT NULL,               -- Date of the transaction
    TransactionType ENUM('Buy', 'Sell') NOT NULL,  -- Indicates whether the transaction is a buy or sell
    AssetType ENUM('Stock', 'Currency') NOT NULL,  -- Type of asset (Stock or Currency)
    AssetName VARCHAR(100) NOT NULL,               -- Name of the asset (e.g., Bitcoin, Apple Stock)
    AssetCode VARCHAR(10),                         -- Code for the asset (e.g., BTC, AAPL)
    Quantity DECIMAL(10, 2) NOT NULL,              -- Quantity of the asset
    UnitPrice DECIMAL(10, 2) NOT NULL,             -- Price per unit in USD
    TotalValue DECIMAL(10, 2) AS (Quantity * UnitPrice) STORED, -- Total value of the transaction
    TaxPercentage DECIMAL(5, 2) NOT NULL,          -- Tax percentage applied
    TaxCollected DECIMAL(10, 2) AS (TotalValue * (TaxPercentage / 100)) STORED, -- Tax collected
    Remarks VARCHAR(255) DEFAULT NULL              -- Optional remarks
);


-- Step 4: Create an index for faster searches on CNIC and AssetType
CREATE INDEX idx_CNIC_AssetType ON CitizenPurchases (CNIC, AssetType);
