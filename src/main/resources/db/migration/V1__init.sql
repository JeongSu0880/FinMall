create table account (
                         applied_rate decimal(5,2) not null,
                         current_installment_count INT UNSIGNED not null,
                         payment_day INT UNSIGNED,
                         total_installment_count INT UNSIGNED not null,
                         accrued_interest bigint not null,
                         balance bigint not null,
                         created_at TIMESTAMP null,
                         id INT UNSIGNED not null auto_increment,
                         last_paid_at datetime(6),
                         maturity_at datetime(6) not null,
                         principal bigint not null,
                         product_id INT UNSIGNED not null,
                         started_at datetime(6) not null,
                         terminated_at datetime(6),
                         updated_at TIMESTAMP null,
                         user_id INT UNSIGNED not null,
                         account_number varchar(30) not null,
                         status enum ('ACTIVE','MATURED','SUSPENDED','TERMINATED') not null,
                         primary key (id)
);

create table bank (
                      created_at TIMESTAMP null,
                      id INT UNSIGNED not null auto_increment,
                      updated_at TIMESTAMP null,
                      name varchar(50) not null,
                      primary key (id)
);

create table product (
                         base_rate decimal(5,2) not null,
                         deposit_protection_limit integer not null,
                         early_withdrawal_rate decimal(5,2) not null,
                         is_fixed bit not null,
                         max_age integer,
                         max_payment_amount integer not null,
                         min_age integer,
                         min_payment_amount integer not null,
                         payment_cycle integer,
                         total_period_days integer not null,
                         bank_id INT UNSIGNED not null,
                         created_at TIMESTAMP null,
                         id INT UNSIGNED not null auto_increment,
                         updated_at TIMESTAMP null,
                         name varchar(30) not null,
                         interest_type enum ('COMPOUND','SIMPLE') not null,
                         product_type enum ('DEPOSIT','FREE','SAVINGS') not null,
                         primary key (id)
);

create table transaction (
                             amount integer not null,
                             account_id INT UNSIGNED not null,
                             balance_after bigint not null,
                             created_at TIMESTAMP null,
                             id INT UNSIGNED not null auto_increment,
                             occurred_at datetime(6) not null,
                             updated_at TIMESTAMP null,
                             description varchar(255),
                             status enum ('COMPLETED','FAILED','PENDING') not null,
                             transaction_type enum ('DEPOSIT','INTEREST','MATURITY','PENALTY','WITHDRAW') not null,
                             primary key (id)
);

create table user (
                      birth_date date not null,
                      enabled bit not null,
                      created_at TIMESTAMP null,
                      id INT UNSIGNED not null auto_increment,
                      updated_at TIMESTAMP null,
                      username varchar(50) not null,
                      password varchar(255) not null,
                      role enum ('ROLE_ADMIN','ROLE_USER') not null,
                      primary key (id)
);