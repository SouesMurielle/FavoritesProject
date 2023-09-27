CREATE DATABASE favorites;
USE favorites;
CREATE USER 'favuser'@'localhost' IDENTIFIED by 'favuser';
GRANT ALL PRIVILEGES ON favorites.* TO 'favuser'@'localhost';