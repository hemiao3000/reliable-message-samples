DROP DATABASE IF EXISTS db_storage;
CREATE DATABASE db_storage
    DEFAULT CHARACTER SET utf8mb4 -- 乱码问题
    DEFAULT COLLATE utf8mb4_0900_ai_ci -- 英文大小写不敏感问题
;
USE db_storage;

DROP TABLE IF EXISTS `storage`;
CREATE TABLE `storage`
(
    `id`             BIGINT(11)       NOT NULL AUTO_INCREMENT,
    `commodity_code` VARCHAR(255)     NULL DEFAULT NULL,
    `count`          INT(11) UNSIGNED NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `commodity_code` (`commodity_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;

INSERT INTO `storage`
VALUES (1, '100202003032041', 10);

drop table if exists `received_message`;
CREATE TABLE `received_message`
(
    id BIGINT NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1;
