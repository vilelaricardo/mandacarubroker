CREATE TABLE users
(
    user_id    VARCHAR PRIMARY KEY,
    username   VARCHAR(255)   NOT NULL UNIQUE,
    password   VARCHAR(255)   NOT NULL,
    email      VARCHAR(255)   NOT NULL UNIQUE,
    first_name VARCHAR(255)   NOT NULL,
    last_name  VARCHAR(255)   NOT NULL,
    birth_date DATE           NOT NULL,
    balance    DECIMAL(10, 2) NOT NULL DEFAULT 0.00
);

CREATE TABLE stock
(
    stock_id     VARCHAR PRIMARY KEY,
    symbol       VARCHAR NOT NULL,
    company_name VARCHAR NOT NULL,
    price        FLOAT   NOT NULL,
    quantity     INT     NOT NULL
);

/*A ideia aqui, é que a partir da transação tendo
o id do usuário e o id da ação, teremos acesso ao
valor da ação na cotação atual e devolveremos o justo
  valor a usuário.*/

CREATE TABLE stock_transaction
(
    transaction_id VARCHAR PRIMARY KEY,
    stock_id       VARCHAR NOT NULL,
    user_id        VARCHAR NOT NULL,
    quantity       INT     NOT NULL,
    FOREIGN KEY (stock_id) REFERENCES stock (stock_id),
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);