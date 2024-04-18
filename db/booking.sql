-- Step 1: Create the tables without foreign key constraints
CREATE TABLE reservation (
  id SERIAL PRIMARY KEY,
  first_name VARCHAR(45),
  last_name VARCHAR(45),
  email VARCHAR(45),
  phone_number VARCHAR(45),
  table_id INT
);

CREATE TABLE table_model (
  id SERIAL PRIMARY KEY,
  title VARCHAR(128) UNIQUE,
  reservation_id INT
);

-- Step 2: Alter the tables to add foreign key constraints
ALTER TABLE reservation
  ADD CONSTRAINT fk_table_id FOREIGN KEY (table_id) REFERENCES table_model (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

ALTER TABLE table_model
  ADD CONSTRAINT fk_reservation_id FOREIGN KEY (reservation_id) REFERENCES reservation (id) ON DELETE NO ACTION ON UPDATE NO ACTION;

