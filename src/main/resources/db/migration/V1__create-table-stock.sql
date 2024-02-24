CREATE TABLE tb_stock(
                      id VARCHAR PRIMARY KEY,
                      symbol VARCHAR NOT NULL,
                      company_name VARCHAR NOT NULL,
                      price FLOAT NOT NULL
);
CREATE TABLE tb_user(
                        id VARCHAR PRIMARY KEY,
                        username VARCHAR NOT NULL UNIQUE ,
                        password VARCHAR NOT NULL,
                        email VARCHAR NOT NULL UNIQUE ,
                        first_name VARCHAR NOT NULL,
                        last_name VARCHAR NOT NULL,
                        birth_date DATE NOT NULL,
                        balance FLOAT NOT NULL
);