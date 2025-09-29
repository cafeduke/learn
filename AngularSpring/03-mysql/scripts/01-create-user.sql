/**
 * Create a user named 'duke'
 *   -- Create a db name 'dukecart'
 *   -- Grant all privileges to 'duke' for database 'dukecart'
 *   -- Now 'duke' can connect without sudo
 */
CREATE USER 'duke'@'localhost' IDENTIFIED BY 'duke';
DROP SCHEMA IF EXISTS `dukecart`;
CREATE SCHEMA `dukecart`;
GRANT ALL PRIVILEGES ON dukecart.* TO 'duke'@'localhost';
