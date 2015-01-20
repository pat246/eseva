-- Create DB script
CREATE DATABASE companies;
USE companies;

-- Creating table for Credentials
CREATE TABLE `credentials` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `company_name` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(32) DEFAULT NULL,
  `last_password_reset_date` datetime DEFAULT NULL,
  `mobile` varchar(128) DEFAULT NULL,
  `contact_person` varchar(128) DEFAULT NULL,
  `address` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;

CREATE TABLE `consultants` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(32) NOT NULL,
  `lname` varchar(32) NOT NULL,
  `addr` varchar(1024) NOT NULL,
  `addr1` varchar(1024) NOT NULL,
  `addr2` varchar(1024) NOT NULL,
  `email` varchar(64) NOT NULL,
  `contact_numbers` varchar(32) NOT NULL,
  `company_name` varchar(1024) NOT NULL ,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

create table `random` (
 `random` varchar(1024)
)