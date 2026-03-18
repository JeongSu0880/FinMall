DROP TABLE IF EXISTS Transaction;
TRUNCATE TABLE Account;
TRUNCATE TABLE Product;
TRUNCATE TABLE Bank;
TRUNCATE TABLE User;

CREATE TABLE Bank (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE User (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    birth_date DATE NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Product (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL,
    bank_id INT UNSIGNED NOT NULL,
    product_type VARCHAR(20) NOT NULL,
    interest_type VARCHAR(20) NOT NULL,
    payment_cycle INT NULL,
    is_fixed BOOLEAN NOT NULL,
    total_period_days INT NOT NULL,
    min_payment_amount INT NOT NULL,
    max_payment_amount INT NOT NULL,
    base_rate DECIMAL(5,2) NOT NULL,
    early_withdrawal_rate DECIMAL(5,2) NOT NULL,
    min_age INT NULL,
    max_age INT NULL,
    deposit_protection_limit INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (bank_id) REFERENCES Bank(id)
);

CREATE TABLE Account (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNSIGNED NOT NULL,
    product_id INT UNSIGNED NOT NULL,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    principal BIGINT NOT NULL DEFAULT 0,
    balance BIGINT NOT NULL DEFAULT 0,
    accrued_interest BIGINT NOT NULL DEFAULT 0,
    applied_rate DECIMAL(5,2) NOT NULL,
    total_installment_count INT UNSIGNED NOT NULL,
    current_installment_count INT UNSIGNED NOT NULL DEFAULT 0,
    payment_day INT UNSIGNED NULL,
    last_paid_at DATETIME NULL,
    started_at DATETIME NOT NULL,
    maturity_at DATETIME NOT NULL,
    terminated_at DATETIME NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES User(id),
    FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE TABLE Transaction (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    account_id INT UNSIGNED NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount INT NOT NULL,
    balance_after BIGINT NOT NULL,
    occurred_at DATETIME NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'COMPLETED',
    description VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES Account(id)
);

-- =========================
-- 1. BANK
-- =========================
INSERT INTO bank (id, name) VALUES
                                (1, '하나은행'),
                                (2, '국민은행'),
                                (3, '신한은행');


-- =========================
-- 2. USER (password는 bcrypt 가짜값)
-- =========================

INSERT INTO user (id, username, password, role, birth_date, enabled) VALUES
                                                                         (1, 'admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'ADMIN', '1990-01-01', true),
                                                                         (2, 'user1@test.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'USER', '1995-05-10', true),
                                                                         (3, 'user2@test.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'USER', '2000-08-20', true);


-- =========================
-- 3. PRODUCT
-- =========================
INSERT INTO product (
    id, name, bank_id, product_type, interest_type,
    payment_cycle, is_fixed, total_period_days,
    min_payment_amount, max_payment_amount,
    base_rate, early_withdrawal_rate,
    min_age, max_age,
    deposit_protection_limit
) VALUES
-- 정기예금
(1, '하나 정기예금 1년', 1, 'DEPOSIT', 'SIMPLE',
 NULL, true, 365,
 1000000, 100000000,
 3.50, 1.00,
 19, NULL,
 100000000),

-- 정기적금
(2, '하나 정기적금 1년', 1, 'SAVINGS', 'SIMPLE',
 30, true, 365,
 10000, 1000000,
 4.00, 1.50,
 19, NULL,
 100000000),

-- 자유적금
(3, '자유 적금 플러스', 2, 'FREE', 'COMPOUND',
 NULL, false, 180,
 1000, 5000000,
 3.20, 1.20,
 NULL, NULL,
 100000000);


-- =========================
-- 4. ACCOUNT
-- =========================
INSERT INTO account (
    id, user_id, product_id,
    account_number,
    status,
    principal, balance, accrued_interest,
    applied_rate,
    total_installment_count, current_installment_count,
    payment_day,
    last_paid_at,
    started_at, maturity_at, terminated_at
) VALUES
-- user1 적금
(1, 2, 2, '111-2222-3333',
 'ACTIVE',
 100000, 100000, 500,
 4.00,
 12, 2,
 10,
 NOW(),
 NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), NULL),

-- user2 예금
(2, 3, 1, '222-3333-4444',
 'ACTIVE',
 5000000, 5000000, 0,
 3.50,
 1, 1,
 NULL,
 NOW(),
 NOW(), DATE_ADD(NOW(), INTERVAL 365 DAY), NULL);


-- =========================
-- 5. TRANSACTION
-- =========================
INSERT INTO transaction (
    id, account_id, transaction_type,
    amount, balance_after,
    occurred_at, status, description
) VALUES
      (1, 1, 'DEPOSIT', 50000, 50000, NOW(), 'COMPLETED', '첫 납입'),
      (2, 1, 'DEPOSIT', 50000, 100000, NOW(), 'COMPLETED', '두 번째 납입'),
      (3, 2, 'DEPOSIT', 5000000, 5000000, NOW(), 'COMPLETED', '예금 가입');


-- =========================
-- 6. IMAGE
-- =========================
INSERT INTO image (id, product_id, image_url, is_thumbnail) VALUES
                                                                (1, 1, '/upload/20260318/deposit1.png', true),
                                                                (2, 1, '/upload/20260318/deposit1_detail.png', false),
                                                                (3, 2, '/upload/20260318/savings1.png', true),
                                                                (4, 3, '/upload/20260318/free1.png', true);