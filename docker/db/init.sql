CREATE DATABASE IF NOT EXISTS study;
USE study;

-- CREATE TABLE IF NOT EXISTS user (
--                                      id INT AUTO_INCREMENT PRIMARY KEY,
--                                      username VARCHAR(50) NOT NULL,
--     email VARCHAR(50) NOT NULL
--     );

INSERT INTO user (username, email) VALUES (
                                           'admin', 'admin@example.com');