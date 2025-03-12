-- Create companies table
CREATE TABLE companies (
    id BIGSERIAL PRIMARY KEY,
    cuit VARCHAR(20) UNIQUE NOT NULL,
    company_name VARCHAR(255) NOT NULL,
    registration_date TIMESTAMP NOT NULL
);

-- Create transactions table
CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    company_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    debit_account VARCHAR(50) NOT NULL,
    credit_account VARCHAR(50) NOT NULL,
    date TIMESTAMP NOT NULL,
    FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Insert 5 companies
INSERT INTO companies (cuit, company_name, registration_date) VALUES
('30-12345678-1', 'Empresa Alpha', '2024-06-15 10:00:00'),
('30-87654321-0', 'Empresa Beta', '2024-09-20 15:30:00'),
('33-98765432-0', 'Empresa Gamma', '2024-02-05 09:15:00'),
('34-90685635-4', 'Empresa Delta', '2024-03-01 11:45:00'),
('33-68521479-8', 'Empresa Epsilon', '2025-03-01 14:20:00');

-- Insert 50 transactions
-- Alpha company (without recent transactions)
INSERT INTO transactions (company_id, amount, debit_account, credit_account, date) VALUES
(1, 500.00, 'ACC-1001', 'ACC-2001', '2024-09-21 10:00:00'),
(1, 750.50, 'ACC-1001', 'ACC-2002', '2024-09-21 14:30:00'),
(1, 300.00, 'ACC-1002', 'ACC-2003', '2024-10-05 09:45:00'),
(1, 900.75, 'ACC-1003', 'ACC-2004', '2024-10-12 16:20:00'),
(1, 150.00, 'ACC-1004', 'ACC-2005', '2024-10-13 08:15:00'),
(1, 450.25, 'ACC-1005', 'ACC-2006', '2024-11-20 13:10:00'),
(1, 700.00, 'ACC-1006', 'ACC-2007', '2024-12-05 17:30:00'),
(1, 600.80, 'ACC-1007', 'ACC-2008', '2024-12-15 12:40:00'),
(1, 800.00, 'ACC-1008', 'ACC-2009', '2024-12-30 15:25:00'),
(1, 950.00, 'ACC-1009', 'ACC-2010', '2025-01-05 09:10:00');

-- Beta company
INSERT INTO transactions (company_id, amount, debit_account, credit_account, date) VALUES
(2, 1200.00, 'ACC-2001', 'ACC-3001', '2024-11-15 10:30:00'),
(2, 500.75, 'ACC-2002', 'ACC-3002', '2024-12-25 11:45:00'),
(2, 650.00, 'ACC-2003', 'ACC-3003', '2024-12-25 14:10:00'),
(2, 700.00, 'ACC-2004', 'ACC-3004', '2024-12-26 09:25:00'),
(2, 800.00, 'ACC-2005', 'ACC-3005', '2025-01-01 16:00:00'),
(2, 900.50, 'ACC-2006', 'ACC-3006', '2025-02-13 08:35:00'), -- Recent
(2, 1000.00, 'ACC-2007', 'ACC-3007', '2025-02-15 14:45:00'), -- Recent
(2, 1100.25, 'ACC-2008', 'ACC-3008', '2025-02-17 18:10:00'), -- Recent
(2, 1200.00, 'ACC-2009', 'ACC-3009', '2025-03-10 12:00:00'), -- Recent
(2, 1300.50, 'ACC-2010', 'ACC-3010', '2025-03-11 09:50:00'); -- Recent

-- Gamma company
INSERT INTO transactions (company_id, amount, debit_account, credit_account, date) VALUES
(3, 250.00, 'ACC-3001', 'ACC-4001', '2024-02-05 10:10:00'),
(3, 350.25, 'ACC-3002', 'ACC-4002', '2024-02-05 14:30:00'),
(3, 450.00, 'ACC-3003', 'ACC-4003', '2024-02-15 11:20:00'),
(3, 550.75, 'ACC-3004', 'ACC-4004', '2024-02-28 08:40:00'),
(3, 650.00, 'ACC-3005', 'ACC-4005', '2025-03-01 15:00:00'), -- Recent
(3, 750.50, 'ACC-3006', 'ACC-4006', '2025-03-03 10:25:00'), -- Recent
(3, 850.00, 'ACC-3007', 'ACC-4007', '2025-03-06 09:30:00'), -- Recent
(3, 950.25, 'ACC-3008', 'ACC-4008', '2025-03-07 17:15:00'), -- Recent
(3, 1050.00, 'ACC-3009', 'ACC-4009', '2025-03-08 13:05:00'), -- Recent
(3, 1150.75, 'ACC-3010', 'ACC-4010', '2025-03-09 11:50:00'); -- Recent

-- Delta company (without recent transactions)
INSERT INTO transactions (company_id, amount, debit_account, credit_account, date) VALUES
(4, 400.00, 'ACC-4001', 'ACC-5001', '2024-03-01 10:10:00'),
(4, 500.25, 'ACC-4002', 'ACC-5002', '2024-03-30 12:00:00'),
(4, 600.50, 'ACC-4003', 'ACC-5003', '2024-05-20 15:20:00'),
(4, 700.75, 'ACC-4004', 'ACC-5004', '2024-05-27 09:40:00'),
(4, 800.00, 'ACC-4005', 'ACC-5005', '2024-12-28 11:30:00');

-- Epsilon company
INSERT INTO transactions (company_id, amount, debit_account, credit_account, date) VALUES
(5, 900.25, 'ACC-5001', 'ACC-6001', '2025-01-02 08:00:00'),
(5, 1000.50, 'ACC-5002', 'ACC-6002', '2025-02-10 14:10:00'),
(5, 1100.75, 'ACC-5003', 'ACC-6003', '2025-02-15 17:20:00'), -- Reciente
(5, 1200.00, 'ACC-5004', 'ACC-6004', '2025-02-18 12:30:00'), -- Reciente
(5, 1300.25, 'ACC-5005', 'ACC-6005', '2025-02-20 10:45:00'); -- Reciente

