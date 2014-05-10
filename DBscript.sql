-- Create DB
CREATE DATABASE companies;
USE companies;

-- Creating table for Credentials
CREATE TABLE credentials
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    company_name VARCHAR(32) NOT NULL, 
    user_id VARCHAR(32) NOT NULL,
    password VARCHAR(32) NOT NULL 
);
