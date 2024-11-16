-- Create the database
DROP DATABASE IF EXISTS organization;
CREATE DATABASE IF NOT EXISTS organization;
USE organization;

DROP TABLE IF EXISTS offerings_clients;
DROP TABLE IF EXISTS offerings;
DROP TABLE IF EXISTS lessons;
DROP TABLE IF EXISTS schedules;
DROP TABLE IF EXISTS instructor_cities;
DROP TABLE IF EXISTS instructors;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS admins;
DROP TABLE IF EXISTS spaces;
DROP TABLE IF EXISTS users;


--user table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_type ENUM('Admin', 'Client', 'Instructor', 'UnderageClient')
);

--clients table
CREATE TABLE clients (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    age INT,
    guardian_name VARCHAR(50),
    guardian_phone VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- instructors table
CREATE TABLE instructors (
    instructor_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    specialization VARCHAR(50),
    phone_number VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

--admins
CREATE TABLE admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

--spaces table
CREATE TABLE spaces (
    space_id INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(100),
    type VARCHAR(50),
    is_rented BOOLEAN,
    city VARCHAR(50),
    person_limit INT
);

-- schedules table
CREATE TABLE schedules (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    start_time TIME,
    end_time TIME,
    start_date DATE,
    end_date DATE,
    day VARCHAR(20),
    space_id INT,
    FOREIGN KEY (space_id) REFERENCES spaces(space_id) ON DELETE CASCADE
);

-- lessons table
CREATE TABLE lessons (
    lesson_id INT AUTO_INCREMENT PRIMARY KEY,
    activity VARCHAR(50),
    schedule_id INT,
    space_id INT,
    is_open BOOLEAN,
    FOREIGN KEY (schedule_id) REFERENCES schedules(schedule_id) ON DELETE CASCADE,
    FOREIGN KEY (space_id) REFERENCES spaces(space_id) ON DELETE CASCADE
);

--offeringstable
CREATE TABLE offerings (
    offering_id INT AUTO_INCREMENT PRIMARY KEY,
    lesson_id INT,
    instructor_id INT,
    booking_amount INT,
    is_public BOOLEAN,
    is_open BOOLEAN,
    FOREIGN KEY (lesson_id) REFERENCES lessons(lesson_id) ON DELETE CASCADE,
    FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id) ON DELETE CASCADE
);

-- offerings_clients relationship
CREATE TABLE offerings_clients (
    offering_id INT,
    client_id INT,
    PRIMARY KEY (offering_id, client_id),
    FOREIGN KEY (offering_id) REFERENCES offerings(offering_id) ON DELETE CASCADE,
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE
);

-- 'instructor_cities relationship
CREATE TABLE instructor_cities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    instructor_id INT NOT NULL,
    city VARCHAR(50) NOT NULL,
    FOREIGN KEY (instructor_id) REFERENCES instructors(instructor_id) ON DELETE CASCADE
);

-- test values for client admin, instructor and spaces
INSERT INTO users (name, password, user_type) VALUES ('admin', '123', 'Admin');
SET @admin_user_id = LAST_INSERT_ID();
INSERT INTO admins (user_id) VALUES (@admin_user_id);

-- Insert a client user
INSERT INTO users (name, password, user_type) VALUES ('client1', '123', 'Client');
SET @client_user_id = LAST_INSERT_ID();
INSERT INTO clients (user_id, age) VALUES (@client_user_id, 25);

-- Insert an instructor user
INSERT INTO users (name, password, user_type) VALUES ('ins', '123', 'Instructor');
SET @instr_user_id = LAST_INSERT_ID();
INSERT INTO instructors (user_id, specialization, phone_number) VALUES (@instr_user_id, 'Yoga', '1234567890');

-- Insert another client user
INSERT INTO users (name, password, user_type) VALUES ('client2', '123', 'Client');
SET @client2_user_id = LAST_INSERT_ID();
INSERT INTO clients (user_id, age) VALUES (@client2_user_id, 30);

INSERT INTO spaces (address, type, is_rented, city, person_limit) VALUES
('123 Main St', 'Studio', FALSE, 'New York', 20),
('456 Broadway Ave', 'Outdoor Area', TRUE, 'Los Angeles', 50),
('789 Market Rd', 'Gym', FALSE, 'San Francisco', 30),
('101 Fitness Ln', 'Yoga Room', TRUE, 'Chicago', 15),
('202 Health Blvd', 'Conference Hall', FALSE, 'Seattle', 100);

SELECT * from spaces;

SELECT * from schedules;

