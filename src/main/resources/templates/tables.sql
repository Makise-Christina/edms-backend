DROP DATABASE IF EXISTS edmsSQL;
CREATE DATABASE edmsSQL DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
use edmsSQL;

CREATE TABLE user (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

    `uuid` CHAR(32) NOT NULL COMMENT '全局id',
    `user_name` CHAR(32) NOT NULL COMMENT '用户名',

    `name` CHAR(32) NOT NULL COMMENT '姓名',
    `role` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户类别 0UNKNOWN 1ADMIN 2WORKER',
    `gender` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户性别 0UNKNOWN 1MALE 2FEMALE',
    `email` VARCHAR(64) COMMENT '邮箱',
    `mobile` CHAR(32) COMMENT '电话',
    `salt` VARCHAR(32) NOT NULL COMMENT '密码加盐',
    `password` VARCHAR(255) NOT NULL COMMENT '密码',

    `position` VARCHAR(32) DEFAULT '' COMMENT '职位',
    `company` VARCHAR(64) DEFAULT '' COMMENT '公司',
    `address` VARCHAR(128) DEFAULT '' COMMENT '地址',
    `department_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '所属部门id',

    `locked` TINYINT UNSIGNED DEFAULT 0 COMMENT '是否冻结',
    `login_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',

    PRIMARY KEY (`id`),

    UNIQUE KEY `uk_uuid` (`uuid`),
    UNIQUE KEY `uk_user_name` (`user_name`),
    UNIQUE KEY `uk_email` (`email`),
    UNIQUE KEY `uk_mobile` (`mobile`)

)ENGINE=InnoDB COMMENT='用户';

INSERT INTO user (
    `uuid`,
    `user_name`,
    `name`,
    `role`,
    `salt`,
    `password`
)
VALUES (
    'abcd@1234',
    'test_user_01',
    'test_user_01',
    1,
    'PHJMBYkR6LGik/BzZkhJ',
    '25e29ee799820aaa8b6e8298e7aacc40'
);

-- 02 部门
CREATE TABLE department (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(32) NOT NULL COMMENT '部门名称',
    `leader_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '领导id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='部门';


-- 03 客户老人对象
CREATE TABLE elder (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `gender` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户性别 0UNKNOWN 1MALE 2FEMALE',
    `name` CHAR(20) NOT NULL COMMENT '姓名',
    `age` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户年龄',
    `marital_status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '婚姻状态 0UNKNOWN 1SINGLE 2MARRIED 3WIDOWED 4DIVORCED',
    `education` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '教育程度 0UNKONWN 1PRIMARY_SCHOOL 2JUNIOR_HIGH_SCHOOL 3SENIOR_HIGH_SCHOOL 4BACHELOR 5MASTER 6PHD',
    `ethnicity` CHAR(32) DEFAULT '' COMMENT '民族',
    `job` CHAR(128) DEFAULT '' COMMENT '职业',
    `company` VARCHAR(128) DEFAULT '' COMMENT '公司',
    `address` VARCHAR(128) DEFAULT '' COMMENT '地址',
    `id_card_number` CHAR(32) DEFAULT '' COMMENT '身份证号码',
    `medical_history` TEXT COMMENT '既往病史',
    `health_insurance` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '医保类型 0UNKNOWN 1PROVINCE 2CITY 3PERSONAL 4NONE',
    `hospital` VARCHAR(128) DEFAULT '' COMMENT '医院',
    `nursing_level` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '护理级别 0UNKNOWN 1HALF_CARE 2FULL_CARE 3SPECIAL_CARE_1 4SPECIAL_CARE_2',
    `demand` TEXT COMMENT '家属要求',
    `records` TEXT COMMENT '入院前情况记录',
    `picture` TEXT COMMENT '照片base64', 

    `building_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '楼',
    `floor_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '层',
    `room_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '房',
    `bed_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '床',

    PRIMARY KEY (`id`)
    
)ENGINE=InnoDB COMMENT='客户老人对象';

-- 04 老人联系人
CREATE TABLE elder_contact (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '姓名',
    `mobile` CHAR(20) NOT NULL COMMENT '手机号',
    `company` VARCHAR(64) DEFAULT '' COMMENT '公司',
    `address` VARCHAR(128) DEFAULT '' COMMENT '地址',
    `relation` TINYINT UNSIGNED DEFAULT 0 COMMENT '与老人关系 0UNKNOWN',

    `elder_id` BIGINT UNSIGNED NOT NULL COMMENT '关联老人id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='老人联系人';

-- 05 楼
CREATE TABLE building (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '楼名称',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='楼';

-- 06 楼层
CREATE TABLE floor (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '楼层名称',

    `building_id` BIGINT UNSIGNED NOT NULL COMMENT '所属楼id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='楼层';

-- 07 房间
CREATE TABLE room (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '房间名称',

    `floor_id` BIGINT UNSIGNED NOT NULL COMMENT '所属楼层id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='房间';

-- 08 床位
CREATE TABLE bed (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '床位名称',

    `room_id` BIGINT UNSIGNED NOT NULL COMMENT '所属房间id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='床位';

INSERT INTO building (`id`, `name`) VALUES (1, 'A楼');
INSERT INTO building (`id`, `name`) VALUES (2, 'B楼');
INSERT INTO building (`id`, `name`) VALUES (3, 'C楼');

INSERT INTO floor (`id`, `name`, `building_id`) VALUES (11, 'A1', 1);
INSERT INTO floor (`id`, `name`, `building_id`) VALUES (12, 'A2', 1);
INSERT INTO floor (`id`, `name`, `building_id`) VALUES (21, 'B1', 2);
INSERT INTO floor (`id`, `name`, `building_id`) VALUES (22, 'B2', 2);
INSERT INTO floor (`id`, `name`, `building_id`) VALUES (23, 'B3', 2);
INSERT INTO floor (`id`, `name`, `building_id`) VALUES (31, 'C1', 3);

INSERT INTO room (`id`, `name`, `floor_id`) VALUES (1101, 'A101', 11);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (1102, 'A102', 11);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (1201, 'A201', 12);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (1202, 'A202', 12);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (2101, 'B101', 21);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (2201, 'B201', 22);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (2301, 'B301', 23);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3101, 'C101', 31);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3102, 'C102', 31);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3103, 'C103', 31);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3104, 'C104', 31);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3105, 'C105', 31);
INSERT INTO room (`id`, `name`, `floor_id`) VALUES (3106, 'C106', 31);

INSERT INTO bed (`id`, `name`, `room_id`) VALUES (110101,'A101-1号床',1101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (110102,'A101-2号床',1101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (110103,'A101-3号床',1101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (110201,'A102-1号床',1102);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (120101,'A201-1号床',1201);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (120201,'A202-1号床',1202);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (210101,'B101-1号床',2101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (310101,'C101-1号床',3101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (310102,'C101-2号床',3101);
INSERT INTO bed (`id`, `name`, `room_id`) VALUES (310103,'C101-3号床',3101);

-- 09 健康记录
CREATE TABLE nursing_record (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `elder_id` BIGINT UNSIGNED NOT NULL COMMENT '关联老人id',
    `employee_id` BIGINT UNSIGNED NOT NULL COMMENT '关联护理人员id',
    `blood_glucose` DECIMAL(8,4) DEFAULT 0.0 COMMENT '血糖',
    `sbp` INT DEFAULT 0 COMMENT '收缩压',
    `dbp` INT DEFAULT 0 COMMENT '舒张压',
    `heart_rate` INT DEFAULT 0 COMMENT '心率',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='健康记录';

-- 10 进出记录
CREATE TABLE access_record (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(20) NOT NULL COMMENT '人员姓名',
    `type` TINYINT UNSIGNED NOT NULL COMMENT '进出状态 0进 1出',
    `reason` VARCHAR(128) DEFAULT '' COMMENT '事由',
    `work_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '创建者ID',
    `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '状态 0PENDING 1IN_PROGRESS 2FINISHED 3CANCELED',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='进出记录';

-- 11 库存物品
CREATE TABLE item (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` CHAR(128) NOT NULL COMMENT '物品名称',
    `unit` CHAR(32) NOT NULL DEFAULT '' COMMENT '单位名称',
    `count` INT NOT NULL DEFAULT 0 COMMENT '库存数量',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='库存物品';

-- 12 库存记录
CREATE TABLE warehouse_record (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `item_id` BIGINT UNSIGNED NOT NULL COMMENT '物品关联id',
    `type` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '类型 0UNKNOWN 1DEPOSIT 2WITHDRAWAL 3LOSS',
    `quantity` INT NOT NULL DEFAULT 0 COMMENT '变更数量',
    `worker_id` BIGINT UNSIGNED NOT NULL COMMENT '执行人员id',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='库存记录';

-- 13 活动记录
CREATE TABLE activity (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `name` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '活动名称',
    `place` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '活动地点',
    `brief` TEXT COMMENT '活动简介',
    `worker_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '联系人id',

    `max_person` INT UNSIGNED NOT NULL DEFAULT 0 COMMENT '最大人数',
    `start_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
    `end_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '结束时间',

    PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='活动记录';

-- 14 活动报名记录
CREATE TABLE activity_register (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `activity_id` BIGINT UNSIGNED NOT NULL COMMENT '活动',
    `participant_id` BIGINT UNSIGNED NOT NULL COMMENT '参与人员',

     PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='活动报名记录';

-- 15 待办事项
CREATE TABLE task (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
    `gmt_create` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',

    `worker_id` BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '执行人id',
    `content` VARCHAR(128) NOT NULL DEFAULT '' COMMENT '任务内容',
    `elder_id` BIGINT UNSIGNED DEFAULT 0 COMMENT '关联老人id（可空)',
    `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '完成状态 0UNDONE 1FINISHED',

     PRIMARY KEY (`id`)
)ENGINE=InnoDB COMMENT='待办事项';