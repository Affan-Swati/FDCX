-- Create the database
CREATE DATABASE NADRA;

-- Use the newly created database
USE NADRA;

-- Create the CitizenInformation table
CREATE TABLE CitizenInformation 
(
    CNIC VARCHAR(13) PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    DateOfBirth DATE NOT NULL,
    Address VARCHAR(255) NOT NULL
);