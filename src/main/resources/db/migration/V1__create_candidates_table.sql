-- Creación de la tabla candidates
CREATE TABLE IF NOT EXISTS `candidates`
(
    `id`               INT AUTO_INCREMENT PRIMARY KEY,
    `name`             VARCHAR(255)                     NOT NULL,
    `email`            VARCHAR(255)                     NOT NULL,
    `gender`           ENUM ('MALE', 'FEMALE', 'OTHER') NOT NULL,
    `phone`            VARCHAR(15)                      NOT NULL,
    `address`          TEXT,
    `salary_expected`  DECIMAL                          NOT NULL,
    `date_of_birth`    DATE,
    `experience_years` INT,
    `education`        VARCHAR(255),
    `created_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`       TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Inserción de datos iniciales
INSERT INTO candidates (name, email, gender, phone, address, salary_expected, date_of_birth, experience_years,
                        education)
VALUES ('John Doe', 'john@example.com', 'MALE', '123-456-7890', '123 Main St, Cityville', 75000.00, '1990-01-15', 5,
        'Bachelor in Computer Science'),
       ('Jane Smith', 'jane@example.com', 'FEMALE', '987-654-3210', '456 Oak St, Townsville', 80000.50, '1988-05-20', 8,
        'Master in Business Administration'),
       ('Sam Johnson', 'sam@example.com', 'OTHER', '555-123-4567', '789 Elm St, Villagetown', 60000.75, '1995-09-10', 3,
        'Bachelor in Electrical Engineering'),
       ('Emily White', 'emily@example.com', 'FEMALE', '777-888-9999', '321 Pine St, Hamletville', 90000.25,
        '1985-12-03', 10, 'Ph.D. in Physics'),
       ('Alex Rodriguez', 'alex@example.com', 'MALE', '111-222-3333', '654 Birch St, Countryside', 70000.90,
        '1992-07-25', 6, 'Bachelor in Marketing');
