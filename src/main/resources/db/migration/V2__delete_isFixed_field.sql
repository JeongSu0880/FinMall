ALTER TABLE `Product` DROP COLUMN `isFixed`;
ALTER TABLE `Product` ADD CONSTRAINT `UK_product_name` UNIQUE (`name`);

ALTER TABLE account DROP COLUMN balance;
ALTER TABLE account DROP COLUMN totalInstallmentCount;