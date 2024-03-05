CREATE TABLE treasury (
    id VARCHAR PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR NOT NULL,
    amount INTEGER NOT NULL,
    type VARCHAR NOT NULL,
    maturity_date TIMESTAMP NOT NULL,
    interest_rate FLOAT NOT NULL,
    price FLOAT NOT NULL
);