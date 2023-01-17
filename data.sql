CREATE DATABASE IF NOT EXISTS CAR_APP;

USE CAR_APP;

CREATE TABLE models(
    name VARCHAR(255) NOT NULL,
    horse_power int,
    PRIMARY KEY (name)
);

CREATE TABLE carsImages(
                           vin VARCHAR(255) NOT NULL UNIQUE,
                           author VARCHAR(255),
                           description VARCHAR(255)
);

CREATE TABLE cars(
    id int NOT NULL AUTO_INCREMENT,
    vin VARCHAR(255) NOT NULL UNIQUE,
    color VARCHAR(255),
    model VARCHAR(255) NOT NULL,
    carImage VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (model) REFERENCES models(name),
    FOREIGN KEY (carImage) REFERENCES carsImages(vin)
);
