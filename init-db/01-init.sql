-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS authen_db;
USE authen_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    picture_url VARCHAR(500),
    provider ENUM('GOOGLE', 'FACEBOOK', 'LOCAL') NOT NULL,
    provider_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Insert sample data
INSERT INTO users (email, name, picture_url, provider, provider_id) VALUES
('john.doe@example.com', 'John Doe', 'https://via.placeholder.com/150', 'GOOGLE', 'google123'),
('jane.smith@example.com', 'Jane Smith', 'https://via.placeholder.com/150', 'FACEBOOK', 'facebook456'),
('bob.johnson@example.com', 'Bob Johnson', 'https://via.placeholder.com/150', 'GOOGLE', 'google789'),
('alice.brown@example.com', 'Alice Brown', 'https://via.placeholder.com/150', 'LOCAL', NULL),
('charlie.davis@example.com', 'Charlie Davis', 'https://via.placeholder.com/150', 'FACEBOOK', 'facebook101'),
('emma.wilson@example.com', 'Emma Wilson', 'https://via.placeholder.com/150', 'GOOGLE', 'google202'),
('michael.taylor@example.com', 'Michael Taylor', 'https://via.placeholder.com/150', 'FACEBOOK', 'facebook303'),
('sarah.anderson@example.com', 'Sarah Anderson', 'https://via.placeholder.com/150', 'LOCAL', NULL),
('david.martinez@example.com', 'David Martinez', 'https://via.placeholder.com/150', 'GOOGLE', 'google404'),
('lisa.garcia@example.com', 'Lisa Garcia', 'https://via.placeholder.com/150', 'FACEBOOK', 'facebook505')
ON DUPLICATE KEY UPDATE
name = VALUES(name),
picture_url = VALUES(picture_url),
provider = VALUES(provider),
provider_id = VALUES(provider_id),
updated_at = CURRENT_TIMESTAMP;