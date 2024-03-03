CREATE TABLE real_estate_investment_funds (
    id VARCHAR PRIMARY KEY UNIQUE NOT NULL,
    name VARCHAR NOT NULL,
    symbol VARCHAR NOT NULL UNIQUE,
    company_name VARCHAR NOT NULL,
    type VARCHAR NOT NULL,
    amount INTEGER NOT NULL,
    price FLOAT NOT NULL
);