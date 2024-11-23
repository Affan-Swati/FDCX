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

-- ENTERIES FOR NADRA DATABASE

INSERT INTO CitizenInformation (CNIC, Name, DateOfBirth,Address) VALUES ("3740583626159","Affan Ahmad","2004-04-12","I-8/2 Islamabad"); 

SELECT * FROM CitizenInformation;
SELECT * FROM CitizenInformation WHERE CNIC = "3740583626159";