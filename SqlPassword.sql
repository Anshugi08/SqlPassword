DROP DATABASE IF EXISTS healthsure;

CREATE DATABASE healthsure;

USE healthsure;
 
-- ===========================

-- 1. User Table (Admin Users)

-- ===========================

CREATE TABLE User (
user_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(255) NOT NULL,
last_name VARCHAR(255) NOT NULL,
user_name VARCHAR(255) NOT NULL UNIQUE, -- Used for login
password VARCHAR(255) NOT NULL, -- Hashed password
email VARCHAR(255) NOT NULL UNIQUE, -- Used for OTP verification
status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'INACTIVE',
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
ON UPDATE CURRENT_TIMESTAMP

);
select * from User;
select * from otp;
select * from password_history;
Update User set status = "INACTIVE" where user_id=1;
CREATE TABLE Otp (
otp_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
email VARCHAR(150) NOT NULL,
otp_code VARCHAR(10) NOT NULL,
reason ENUM('SIGNUP', 'FORGOT_PASSWORD') NOT NULL,
status ENUM('INACTIVE', 'ACTIVE', 'EXPIRED') NOT NULL DEFAULT 'ACTIVE',
created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
expires_at DATETIME NOT NULL
);
 
CREATE TABLE password_history (
id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
user_id INT NOT NULL,
password_hash VARCHAR(255) NOT NULL,
changed_at DATETIME DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (user_id) REFERENCES User(user_id)
)





-----------------------------------------------------------------
--------------------------------------------------------

shulekha table
-------------------------------------------------------------



DROP TABLE IF EXISTS otp;

DROP TABLE IF EXISTS recipient;

-- Create Recipient table

CREATE TABLE recipient (

    h_id VARCHAR(20) PRIMARY KEY,

    first_name VARCHAR(100) NOT NULL,

    last_name VARCHAR(100) NOT NULL,

    mobile VARCHAR(10) UNIQUE,

    user_name VARCHAR(100) UNIQUE NOT NULL,

    gender ENUM('MALE', 'FEMALE') NOT NULL,

    dob DATE NOT NULL,

    address VARCHAR(255) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    password VARCHAR(255),

    email VARCHAR(150) UNIQUE NOT NULL,

    status ENUM('ACTIVE', 'INACTIVE') DEFAULT 'ACTIVE'

);

-- Create OTP table

CREATE TABLE Recipient_Otp (

    otp_id INT PRIMARY KEY AUTO_INCREMENT,

    user_name VARCHAR(100) UNIQUE NOT NULL,

    otp_code INT NOT NULL,

    new_password VARCHAR(255),

    status ENUM('PENDING', 'VERIFIED', 'EXPIRED') DEFAULT 'PENDING',

    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    expires_at DATETIME NOT NULL,

    purpose ENUM('REGISTER','FORGOT_PASSWORD') NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_name)

 
 