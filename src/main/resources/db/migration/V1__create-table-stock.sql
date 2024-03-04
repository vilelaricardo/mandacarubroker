CREATE TABLE stock (
    id VARCHAR PRIMARY KEY,
    symbol VARCHAR NOT NULL,
    company_name VARCHAR NOT NULL,
    price FLOAT NOT NULL
);

CREATE TABLE users (
    id VARCHAR PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00
);