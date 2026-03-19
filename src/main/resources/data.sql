SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE Transaction;
TRUNCATE TABLE Account;
TRUNCATE TABLE Product;
TRUNCATE TABLE Bank;
TRUNCATE TABLE image;
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
-- 2. Product
-- =========================
INSERT INTO Product (
    id, name, bankId, productType, interestType,
    paymentCycle, totalPeriodDays,
    minPaymentAmount, maxPaymentAmount,
    baseRate, earlyWithdrawalRate,
    minAge, maxAge,
    depositProtectionLimit
) VALUES
      (1, '자유 입출금', 2, 'FREE', 'SIMPLE',
       NULL, 180,
       1000, 5000000,
       3.20, 1.20,
       NULL, NULL,
       100000000),

      (2, '하나 예금', 1, 'DEPOSIT', 'COMPOUND',
       NULL, 365,
       1000000, 100000000,
       3.50, 1.00,
       19, NULL,
       100000000),

      (3, '하나 정기적금 1년', 1, 'SAVINGS', 'SIMPLE',
       30, 365,
       10000, 1000000,
       4.00, 1.50,
       19, NULL,
       100000000);

-- =========================
-- Image
-- =========================
INSERT INTO image (productId, imageUrl, isThumbnail) VALUES
     (1, 'https://example.com/products/1/main.jpg', TRUE),
     (1, 'https://example.com/products/1/detail1.jpg', FALSE),
     (1, 'https://example.com/products/1/detail2.jpg', FALSE),
     (2, 'https://example.com/products/2/main.jpg', TRUE),
     (2, 'https://example.com/products/2/detail1.jpg', FALSE),
     (3, 'https://example.com/products/3/main.jpg', TRUE),
     (3, 'https://example.com/products/3/detail1.jpg', FALSE),
     (3, 'https://example.com/products/3/detail2.jpg', FALSE),
     (3, 'https://example.com/products/3/detail3.jpg', FALSE);