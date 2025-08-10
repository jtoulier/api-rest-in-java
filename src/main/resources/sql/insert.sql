/*
CREATE TABLE dbo.orders
(
    orderId INT IDENTITY NOT NULL,
    businessId VARCHAR(16),
    businessName VARCHAR(256),
    orderState VARCHAR(32),
    writtenAt DATETIME2(2),
    author VARCHAR(32)
);

CREATE TABLE dbo.credits
(
    orderId INT NOT NULL,
    amount NUMERIC(15, 2),
    interestRate NUMERIC(5, 2),
    dueDate DATE,
    writtenAt DATETIME2(2),
    author VARCHAR(32)
)
*/
INSERT INTO orders (orderId, businessId, businessName, orderState, writtenAt, author) VALUES (1,'100153756', 'GMD S.A.', 'EN PROCESO', GETDATE(), 'JOSEPH');
INSERT INTO credits (orderId, amount, interestRate, dueDate, writtenAt, author) VALUES (1, 852.63, 15.75, '2025-07-25', GETDATE(), 'JOSEPH');

ALTER TABLE orders ALTER COLUMN orderId RESTART WITH 2;