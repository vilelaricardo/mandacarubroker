DROP TABLE IF EXISTS stock;

CREATE TABLE stock (
      id VARCHAR PRIMARY KEY,
      symbol VARCHAR NOT NULL,
      company_name VARCHAR NOT NULL,
      price FLOAT NOT NULL
);

INSERT INTO stock ("id", "symbol", "company_name", "price")
VALUES
    ('b2d13c5a-3df0-4673-b3e6-49244f395ac9', 'AP1', 'Apple Inc.', 100.00),
    ('b2d13c5a-3df0-4673-b3e6-49244f395ad9', 'GO2', 'Alphabet Inc.', 200.00),
    ('b2d13c5a-3df0-4673-b3e6-49244f395ae9', 'MS3', 'Microsoft Corporation', 300.00),
    ('b2d13c5a-3df0-4673-b3e6-49244f395af9', 'AM4', 'Amazon.com Inc.', 400.00),
    ('b2d13c5a-3df0-4673-b3e6-49244f395a99', 'FB5', 'Facebook Inc.', 500.00)
    ON CONFLICT (id) DO NOTHING;
