DROP DATABASE IF EXISTS edmsSQL;
CREATE DATABASE edmsSQL DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
use edmsSQL;

CREATE TABLE user (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    `uuid` CHAR(32) NOT NULL COMMENT '全局id',
    `user_name` CHAR(32) NOT NULL COMMENT '用户名',

    `name` CHAR(32) NOT NULL COMMENT '姓名',
    `type` TINYINT UNSIGNED NOT NULL COMMENT '用户类别',
    `email` VARCHAR(64) COMMENT '邮箱',
    `mobile` CHAR(32) COMMENT '电话',
    `salt` VARCHAR(32) NOT NULL COMMENT '密码加盐',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',

    `locked` TINYINT UNSIGNED DEFAULT 0 COMMENT '是否冻结',
    `login_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',

    PRIMARY KEY (`id`),

    UNIQUE KEY `uk_uuid` (`uuid`),
    UNIQUE KEY `uk_user_name` (`user_name`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_mobile` (`mobile`)

)ENGINE=InnoDB COMMENT='用户';