/*
 * simple_company sql schema 
 * Made by Johnny Edgett, Michael Franceus, Mohammad Khan
 * CS4347.001
 * Project 1
 * Based on the company-schema.mysql by Michael Christiansen
 */

-- Create the Schema and then set it as schema we want to use
CREATE SCHEMA IF NOT EXISTS `simple_company` DEFAULT CHARACTER SET `utf8`;
USE `simple_company`;

-- Create the table for Customer --
CREATE TABLE IF NOT EXISTS `simple_company`.`Customer` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`firstName` VARCHAR(45) NOT NULL,
	`lastName` VARCHAR(45) NOT NULL,
	`gender` CHAR(1) NOT NULL,
	`dob` DATE NOT NULL,
	`email` VARCHAR(45) NOT NULL,
	`address` VARCHAR(45) NOT NULL,
	`creditCard` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- Create the table for Product --

CREATE TABLE IF NOT EXISTS `simple_company`.`Product` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`prodName` VARCHAR(45) NOT NULL,
	`prodDescription` VARCHAR(45) NOT NULL,
	`prodCategory` INT NOT NULL,
	`prodUPC` VARCHAR(45) NOT NULL,
	PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
	
-- Create the table for Purchase --

CREATE TABLE IF NOT EXISTS `simple_company`.`Purchase` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`productID` INT NOT NULL,
	`customerID` INT NOT NULL,
	`purchaseDate` DATE NOT NULL,
	`purchaseAmount` NUMERIC(15,2) NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `purchase_ibfk_1`
		FOREIGN KEY (`customerID`)
		REFERENCES `simple_company`.`Customer` (`id`),
	CONSTRAINT `purchase_ibfk_2`
		FOREIGN KEY (`productID`)
		REFERENCES `simple_company`.`Product` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- Create the table for Address --

CREATE TABLE IF NOT EXISTS `simple_company`.`Address` (
	`address1` VARCHAR(45) NOT NULL,
	`address2` VARCHAR(45) NULL DEFAULT NULL,
	`city` VARCHAR(45) NOT NULL,
	`state` CHAR(2) NOT NULL,
	`zipcode` CHAR(45) NOT NULL,
	`customerID` INT NOT NULL,
	INDEX `customerID` (`customerID` ASC),
	CONSTRAINT `address_ibfk_1`
		FOREIGN KEY(`customerID`)
		REFERENCES `simple_company`.`Customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

-- Create the table for CreditCard --
CREATE TABLE IF NOT EXISTS `simple_company`.`CreditCard` (
	`name` VARCHAR(45) NOT NULL,
	`ccNumber` VARCHAR(45) NOT NULL,
	`expDate` VARCHAR(5) NOT NULL,
	`securityCode` VARCHAR(5) NOT NULL,
	`customerID` INT NOT NULL,
	INDEX `customerID` (`customerID` ASC),
	CONSTRAINT `creditcard_ibfk_1`
		FOREIGN KEY(`customerID`)
		REFERENCES `simple_company`.`Customer` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;