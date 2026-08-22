# Introduction

This document details installing and setting up database with sample data.

# Installation

```bash
> sudo apt install mysql-server
```

# User Configuration

## Login with sudo to create user

https://stackoverflow.com/questions/37239970/connect-to-mysql-server-without-sudo

```mysql
> mysql --version
mysql  Ver 8.0.43-0ubuntu0.24.04.2 for Linux on x86_64 ((Ubuntu))

> sudo mysql
mysql> CREATE USER 'duke'@'localhost' IDENTIFIED BY 'duke';
mysql> DROP SCHEMA IF EXISTS `dukecart`;
mysql> CREATE SCHEMA `dukecart`;
mysql> GRANT ALL PRIVILEGES ON dukecart.* TO 'duke'@'localhost';
mysql> exit
```

## User login without sudo

https://stackoverflow.com/questions/37239970/connect-to-mysql-server-without-sudo

```bash
> mysql -u duke -p
mysql > 
```

# Create and populate DB

```mysql
> mysql -u duke -p
mysql> source scripts/02-create-products.sql;
mysql> use dukecart;
mysql> show tables;
+--------------------+
| Tables_in_dukecart |
+--------------------+
| category           |
| product            |
+--------------------+
2 rows in set (0.00 sec)

mysql> select id,sku,name,unit_price,units_in_stock,date_created from product;
+----+----------------+--------------------------------------------+------------+----------------+----------------------------+
| id | sku            | name                                       | unit_price | units_in_stock | date_created               |
+----+----------------+--------------------------------------------+------------+----------------+----------------------------+
|  1 | BOOK-TECH-1000 | JavaScript - The Fun Parts                 |      19.99 |            100 | 2025-09-29 14:22:35.000000 |
|  2 | BOOK-TECH-1001 | Spring Framework Tutorial                  |      29.99 |            100 | 2025-09-29 14:22:35.000000 |
|  3 | BOOK-TECH-1002 | Kubernetes - Deploying Containers          |      24.99 |            100 | 2025-09-29 14:22:35.000000 |
|  4 | BOOK-TECH-1003 | Internet of Things (IoT) - Getting Started |      29.99 |            100 | 2025-09-29 14:22:35.000000 |
|  5 | BOOK-TECH-1004 | The Go Programming Language: A to Z        |      24.99 |            100 | 2025-09-29 14:22:35.000000 |
+----+----------------+--------------------------------------------+------------+----------------+----------------------------+
5 rows in set (0.00 sec)
```

