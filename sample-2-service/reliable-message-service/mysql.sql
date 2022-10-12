DROP DATABASE IF EXISTS db_message;
CREATE DATABASE db_message
    DEFAULT CHARACTER SET utf8mb4 -- 乱码问题
    DEFAULT COLLATE utf8mb4_0900_ai_ci -- 英文大小写不敏感问题
;
USE db_message;

drop table if exists `message`;
create table `message`
(
    `id`              BIGINT AUTO_INCREMENT,
    `exchange`        VARCHAR(128)  COMMENT '要发往的交换机',
    `routing_key`     VARCHAR(128)  COMMENT '消息的路由键',
    `message_content` VARCHAR(4096) COMMENT '消息的内容',
    `status`          VARCHAR(128)  COMMENT '消息的状态：发送中、已发送、已接收',
    `retry_count`     INT           COMMENT '重发次数',
--  `version`         BIGINT        COMMENT '乐观锁',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB;



