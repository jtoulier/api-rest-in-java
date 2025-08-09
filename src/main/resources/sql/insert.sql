INSERT INTO orders (orderId, businessId, businessName, orderState, writtenAt, author) VALUES (1,'100153756', 'GMD S.A.', 'EN PROCESO', GETDATE(), 'JOSEPH');
INSERT INTO credits (orderId, amount, interestRate, dueDate, writtenAt, author) VALUES (1, 852.63, 15.75, '2025-07-25', GETDATE(), 'JOSEPH');

ALTER TABLE orders ALTER COLUMN orderId RESTART WITH 2;