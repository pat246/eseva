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

CREATE TABLE consultants
(
    id INT UNSIGNED NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id),
    fname VARCHAR(32) NOT NULL, 
    lname VARCHAR(32) NOT NULL, 
    addr VARCHAR(1024) NOT NULL, 
    addr1 VARCHAR(1024) NOT NULL, 
    addr2 VARCHAR(1024) NOT NULL, 
    email VARCHAR(64) NOT NULL, 
    contact_numbers VARCHAR(32) NOT NULL 
);
