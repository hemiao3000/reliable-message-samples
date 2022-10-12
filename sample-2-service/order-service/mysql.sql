DROP DATABASE IF EXISTS db_order;
CREATE DATABASE db_order
    DEFAULT CHARACTER SET utf8mb4 -- 乱码问题
    DEFAULT COLLATE utf8mb4_0900_ai_ci -- 英文大小写不敏感问题
;
USE db_order;

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order`
(
    `id`             BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `user_id`        VARCHAR(255) NULL DEFAULT NULL,
    `commodity_code` VARCHAR(255) NULL DEFAULT NULL,
    `count`          INT(11)      NULL DEFAULT 0,
    `money`          INT(11)      NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;


