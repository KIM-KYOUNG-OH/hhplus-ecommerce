drop table if exists member;
drop table if exists wallet;
drop table if exists transaction_history;
drop table if exists brand;
drop table if exists product;
drop table if exists product_option;
drop table if exists product_statistics;
drop table if exists coupon;
drop table if exists my_coupon;
drop table if exists `order`;
drop table if exists order_item;
drop table if exists payment;

CREATE TABLE `member` (
    `member_id` BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `member_name` VARCHAR(255) NOT NULL,
    `created_at` datetime(6),
    `updated_at` datetime(6)
) ENGINE=InnoDB;

CREATE TABLE `wallet` (
    `member_id` bigint NOT NULL,
    `balance` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`member_id`)
) ENGINE=InnoDB;

CREATE TABLE `transaction_history` (
   `hist_no` bigint NOT NULL AUTO_INCREMENT,
   `transaction_type` varchar(255) NOT NULL,
   `amount` bigint NOT NULL,
   `created_at` datetime(6) DEFAULT NULL,
   PRIMARY KEY (`hist_no`)
) ENGINE=InnoDB;

CREATE TABLE `brand` (
    `brand_id` bigint NOT NULL AUTO_INCREMENT,
    `brand_name` varchar(255) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB;

CREATE TABLE `product` (
    `product_id` bigint NOT NULL AUTO_INCREMENT,
    `product_name` varchar(255) NOT NULL,
    `brand_id` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`product_id`)
) ENGINE=InnoDB;

CREATE TABLE `product_option` (
    `product_option_id` bigint NOT NULL AUTO_INCREMENT,
    `option_name` varchar(255) NOT NULL,
    `quantity` bigint NOT NULL,
    `regular_price` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`product_option_id`)
) ENGINE=InnoDB;

CREATE TABLE `product_statistics` (
    `product_statistics_id` bigint NOT NULL AUTO_INCREMENT,
    `product_id` bigint NOT NULL,
    `target_date` date NOT NULL,
    `order_quantity` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`product_statistics_id`)
) ENGINE=InnoDB;

CREATE TABLE `coupon` (
    `coupon_id` bigint NOT NULL AUTO_INCREMENT,
    `coupon_name` varchar(255) NOT NULL,
    `discount_type` varchar(255) NOT NULL,
    `discount_amount` bigint NOT NULL,
    `quantity` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB;

CREATE TABLE `my_coupon` (
    `coupon_issued_id` bigint NOT NULL AUTO_INCREMENT,
    `member_id` bigint NOT NULL,
    `coupon_id` bigint NOT NULL,
    `is_used` bit(1) NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `update_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`coupon_issued_id`)
) ENGINE=InnoDB;

CREATE TABLE `order` (
    `order_id` bigint NOT NULL AUTO_INCREMENT,
    `order_status` varchar(255) NOT NULL,
    `member_id` bigint NOT NULL,
    `coupon_issued_id` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `UKeqvf7xqjil3mp5ca599cona9m` (`coupon_issued_id`)
) ENGINE=InnoDB;

CREATE TABLE `order_item` (
    `order_id` bigint NOT NULL,
    `product_id` bigint NOT NULL,
    `product_option_id` bigint NOT NULL,
    `quantity` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`order_id`,`product_id`,`product_option_id`)
) ENGINE=InnoDB;

CREATE TABLE `payment` (
    `payment_id` bigint NOT NULL AUTO_INCREMENT,
    `payment_status` varchar(255) NOT NULL,
    `total_regular_price` bigint NOT NULL,
    `total_discount_price` bigint NOT NULL,
    `total_sale_price` bigint NOT NULL,
    `order_id` bigint NOT NULL,
    `created_at` datetime(6) DEFAULT NULL,
    `updated_at` datetime(6) DEFAULT NULL,
    PRIMARY KEY (`payment_id`),
    UNIQUE KEY `UKmf7n8wo2rwrxsd6f3t9ub2mep` (`order_id`)
) ENGINE=InnoDB;

INSERT INTO member(member_id, member_name, created_at, updated_at) VALUES (1, '김항해', NOW(), NOW());
INSERT INTO member(member_id, member_name, created_at, updated_at) VALUES (2, '홍길동', NOW(), NOW());
INSERT INTO wallet (member_id, balance, created_at, updated_at) VALUES(1, 0, NOW(), NOW());
INSERT INTO wallet (member_id, balance, created_at, updated_at) VALUES(2, 10000, NOW(), NOW());
INSERT INTO brand(brand_id, BRAND_NAME, CREATED_AT, UPDATED_AT) VALUES (1, '나이키', NOW(), NOW());
INSERT INTO brand(brand_id, BRAND_NAME, CREATED_AT, UPDATED_AT) VALUES (2, '아디다스', NOW(), NOW());
INSERT INTO product(product_id, PRODUCT_NAME, BRAND_ID, CREATED_AT, UPDATED_AT) VALUES(1, '바지', 1, NOW(), NOW());
INSERT INTO product(product_id, PRODUCT_NAME, BRAND_ID, CREATED_AT, UPDATED_AT) VALUES(2, '셔츠', 1, NOW(), NOW());
INSERT INTO product(product_id, PRODUCT_NAME, BRAND_ID, CREATED_AT, UPDATED_AT) VALUES(3, '양말', 2, NOW(), NOW());
INSERT INTO product(product_id, PRODUCT_NAME, BRAND_ID, CREATED_AT, UPDATED_AT) VALUES(4, '신발', 2, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (1, '파란색', 100, 5000, 1, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (2, '노란색', 300, 6000, 1, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (3, '스몰', 150, 2000, 2, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (4, '라지', 200, 3000, 2, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (5, '미디움', 400, 3000, 2, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (6, '흰색', 150, 6000, 3, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (7, '250', 50, 23000, 4, NOW(), NOW());
INSERT INTO product_option(product_option_id, option_name, QUANTITY, REGULAR_PRICE, PRODUCT_ID ,CREATED_AT, UPDATED_AT) VALUES (8, '280', 20, 22000, 4, NOW(), NOW());
INSERT INTO product_statistics(product_statistics_id, PRODUCT_ID, TARGET_DATE, ORDER_QUANTITY, CREATED_AT, UPDATED_AT) VALUES (1, 1, STR_TO_DATE('20250112', '%Y%m%d'), 5, NOW(), NOW());
INSERT INTO product_statistics(product_statistics_id, PRODUCT_ID, TARGET_DATE, ORDER_QUANTITY, CREATED_AT, UPDATED_AT) VALUES (2, 2, STR_TO_DATE('20250113', '%Y%m%d'), 5000, NOW(), NOW());
INSERT INTO product_statistics(product_statistics_id, PRODUCT_ID, TARGET_DATE, ORDER_QUANTITY, CREATED_AT, UPDATED_AT) VALUES (3, 3, STR_TO_DATE('20250111', '%Y%m%d'), 5, NOW(), NOW());
INSERT INTO product_statistics(product_statistics_id, PRODUCT_ID, TARGET_DATE, ORDER_QUANTITY, CREATED_AT, UPDATED_AT) VALUES (4, 3, STR_TO_DATE('20250112', '%Y%m%d'), 55, NOW(), NOW());
INSERT INTO coupon (coupon_id, coupon_name, discount_type, discount_amount, quantity, created_at, updated_at) VALUES(1, '선착순 정율 쿠폰', 'RATE', 10, 100, NOW(), NOW());
INSERT INTO coupon (coupon_id, coupon_name, discount_type, discount_amount, quantity, created_at, updated_at) VALUES(2, '선착순 정액 쿠폰', 'FIXED', 1000, 50, NOW(), NOW());
INSERT INTO my_coupon (coupon_issued_id, member_id, coupon_id, is_used, created_at, update_at) VALUES(1, 2, 1, 0, NOW(), NOW());
INSERT INTO `order` (order_id, order_status, member_id, coupon_issued_id, created_at, updated_at) VALUES(1, 'COMPLETED', 2, 1, NOW(), NOW());


