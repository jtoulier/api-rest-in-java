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
);