DROP DATABASE IF EXISTS db_account;
CREATE DATABASE db_account
    DEFAULT CHARACTER SET utf8mb4 -- 乱码问题
    DEFAULT COLLATE utf8mb4_0900_ai_ci -- 英文大小写不敏感问题
;
USE db_account;

DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`
(
    `id`      bigint(11)       NOT NULL AUTO_INCREMENT,
    `user_id` varchar(255)     NULL DEFAULT NULL,
    `money`   int(11) UNSIGNED NULL DEFAULT 0,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

INSERT INTO `account` VALUE (1, 'user202103032042012', 1000);

drop table if exists `received_message`;
CREATE TABLE `received_message`
(
    id BIGINT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;