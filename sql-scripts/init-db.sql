CREATE DATABASE IF NOT EXISTS demo;
USE demo;

CREATE TABLE reservation (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id VARCHAR(128) UNIQUE,
    first_name VARCHAR(45),
    last_name VARCHAR(45),
    email VARCHAR(45),
    phone_number VARCHAR(45),
    table_id INT
);

CREATE TABLE table_model (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(128) UNIQUE,
    reservation_id INT
);

ALTER TABLE reservation
    ADD CONSTRAINT fk_table_id FOREIGN KEY (table_id) REFERENCES table_model (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE table_model
    ADD CONSTRAINT fk_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

