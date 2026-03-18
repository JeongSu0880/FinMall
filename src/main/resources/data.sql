SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Transaction;
TRUNCATE TABLE Account;
TRUNCATE TABLE Product;
TRUNCATE TABLE Bank;
TRUNCATE TABLE User;
TRUNCATE TABLE Image;
SET FOREIGN_KEY_CHECKS = 1;

SET time_zone = 'Asia/Seoul';

-- =========================
-- 1. Bank
-- =========================
INSERT INTO Bank (id, name) VALUES
    (1, '하나은행'),
    (2, '국민은행'),
    (3, '신한은행');


-- =========================
-- 2. User (password는 bcrypt 가짜값)
-- =========================

INSERT INTO User (id, username, password, role, birthDate, enabled) VALUES
    (1, 'admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'ADMIN', '1990-01-01', true),
    (2, 'user1@test.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'USER', '1995-05-10', true),
    (3, 'user2@test.com', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36w3C5nK5eQof8I4eAbOe3W', 'USER', '2000-08-20', true);


-- =========================
-- 3. Product
-- =========================
INSERT INTO Product (
    id, name, bank_id, productType, interestType,
    paymentCycle, isFixed, totalPeriodDays,
    minPaymentAmount, maxPaymentAmount,
    baseRate, earlyWithdrawalRate,
    minAge, maxAge,
    depositProtectionLimit
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
-- 4. Account
-- =========================
INSERT INTO Account (
    id, user_id, product_id,
    accountNumber,
    status,
    principal, balance, accruedInterest,
    appliedRate,
    totalInstallmentCount, currentInstallmentCount,
    paymentDay,
    lastPaidAt,
    startedAt, maturityAt, terminatedAt
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
-- 5. Transaction
-- =========================
INSERT INTO Transaction (
    id, account_id, transactionType,
    amount, balanceAfter,
    occurredAt, status, description
) VALUES
    (1, 1, 'DEPOSIT', 50000, 50000, NOW(), 'COMPLETED', '첫 납입'),
    (2, 1, 'DEPOSIT', 50000, 100000, NOW(), 'COMPLETED', '두 번째 납입'),
    (3, 2, 'DEPOSIT', 5000000, 5000000, NOW(), 'COMPLETED', '예금 가입');


-- =========================
-- 6. Image
-- =========================
INSERT INTO Image (id, product_id, thumbnailPath, imagePath) VALUES
    (1, 1, '/upload/20260318/deposit1_thumb.png', '/upload/20260318/deposit1.png'),
    (2, 1, '/upload/20260318/deposit1_detail_thumb.png', '/upload/20260318/deposit1_detail.png'),
    (3, 2, '/upload/20260318/savings1_thumb.png', '/upload/20260318/savings1.png'),
    (4, 3, '/upload/20260318/free1_thumb.png', '/upload/20260318/free1.png');