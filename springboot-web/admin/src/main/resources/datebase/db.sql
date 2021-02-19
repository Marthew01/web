-- drop database ttmm;
create database ttmm;
use ttmm;

CREATE TABLE `hibernate_sequence` (
    `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8  COMMENT "下标";

create table user_t (
                        id int primary key auto_increment COMMENT "ID",
                        account varchar(20) not null unique COMMENT "帐号",
                        password varchar(80) not null COMMENT "密码",
                        username varchar(20) not null COMMENT "昵称",
                        salt varchar(255) not null COMMENT "盐",
                        experience varchar(255) not null COMMENT "财富",
                        sex varchar(10) not null COMMENT "性别",
                        city varchar(255) not null COMMENT "居住地",
                        classify varchar(255) not null COMMENT "职业",
                        wealth bigint not null COMMENT "财富",
                        create_date datetime not null COMMENT "注册时间"
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT "用户表";

CREATE TABLE role_t (
                        id int primary key auto_increment COMMENT "ID",
                        role varchar(255) not null unique COMMENT "角色名称:admin,user,guest"
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT "角色表";


CREATE TABLE permission_t (
                              id int primary key auto_increment COMMENT "ID",
                              name varchar(255) COMMENT "权限:Retrieve,Create,Update,Delete"
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT "权限表";

CREATE TABLE user_role_t (
                             id int primary key auto_increment COMMENT "ID",
                             uid int not null COMMENT '用户id',
                             rid int not null COMMENT '角色id',
                             foreign key (uid) references user_t (id),
                             foreign key (rid) references role_t (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT "用户角色表";

CREATE TABLE role_permission_t (
                                   id int primary key auto_increment COMMENT "ID",
                                   rid int not null COMMENT '角色id',
                                   pid int not null COMMENT '权限id',
                                   foreign key (rid) references role_t (id),
                                   foreign key (pid) references permission_t (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT "角色资源表";


delete FROM `hibernate_sequence`;
INSERT INTO `hibernate_sequence` VALUES (28);
delete FROM `permission_t`;
INSERT INTO `permission_t` VALUES (1, 'Retrieve');
INSERT INTO `permission_t` VALUES (2, 'Create');
INSERT INTO `permission_t` VALUES (3, 'Update');
INSERT INTO `permission_t` VALUES (4, 'Delete');
delete FROM `role_t`;
INSERT INTO `role_t` VALUES (1, 'guest');
INSERT INTO `role_t` VALUES (2, 'user');
INSERT INTO `role_t` VALUES (3, 'admin');
delete from `user_t`;
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (1, '北京', '程序猿', '2021-01-12 18:47:49', 999, 'c4bcff1909b6aac57fbf88aca1088ffa', 'bb14c90e937f08e6c200901704409997', '男', 'admin', 1000, 'admin');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (2, '山西', '老师', '2021-01-12 18:47:52', 555, '2a646ecf27bd37d4abc03029fe3455c4', 'f0b935ae29d1f0767b37d0852c15d469', '男', 'zhang', 522000, 'zhang');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (3, '广东', '打工仔', '2021-01-12 18:47:57', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li', -100, 'li');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (4, '广东', '打工仔1', '2021-01-12 18:47:57', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li1', -100, 'li1');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (5, '广东', '打工仔2', '2021-01-12 18:47:58', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li2', -100, 'li2');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (6, '广东', '打工仔3', '2021-01-12 18:47:59', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li3', -100, 'li3');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (7, '广东', '打工仔4', '2021-01-12 19:47:57', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li4', -100, 'li4');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (8, '广东', '打工仔5', '2021-01-12 19:48:57', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li5', -100, 'li5');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (9, '广东', '打工仔6', '2021-01-12 19:48:58', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li6', -100, 'li6');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (10, '广东', '打工仔7', '2021-01-12 19:48:59', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li7', -100, 'li7');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (11, '广东', '打工仔8', '2021-01-12 20:01:01', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li8', -100, 'li8');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (12, '广东', '打工仔9', '2021-01-12 20:01:02', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li9', -100, 'li9');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (13, '广东', '打工仔11', '2021-01-12 20:01:03', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li10', -100, 'li10');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (14, '广东', '打工仔22', '2021-01-12 20:01:04', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li11', -100, 'li11');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (15, '广东', '打工仔33', '2021-01-12 20:01:05', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li12', -100, 'li12');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (16, '广东', '打工仔44', '2021-01-12 20:01:06', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li13', -100, 'li13');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (17, '广东', '打工仔55', '2021-01-12 20:01:07', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li14', -100, 'li14');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (18, '广东', '打工仔66', '2021-01-12 20:01:08', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li15', -100, 'li15');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (19, '广东', '打工仔77', '2021-01-12 20:01:09', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li16', -100, 'li16');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (20, '广东', '打工仔88', '2021-01-12 20:01:10', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li17', -100, 'li17');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (21, '广东', '打工仔99', '2021-01-12 20:01:11', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li18', -100, 'li18');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (22, '广东', '打工仔w', '2021-01-12 20:01:12', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li19', -100, 'li19');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (23, '广东', '打工仔a', '2021-01-12 20:01:13', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li20', -100, 'li20');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (24, '广东', '打工仔d', '2021-01-12 20:01:14', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li21', -100, 'li21');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (25, '广东', '打工仔x', '2021-01-12 20:01:15', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li22', -100, 'li22');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (26, '广东', '打工仔z', '2021-01-12 20:01:16', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li23', -100, 'li23');
INSERT INTO user_t(id,city,classify,create_date,experience,`password`,salt,sex,username,wealth,account) VALUES (27, '广东', '打工仔f', '2021-01-12 20:01:17', 1, '8e0d21343239c5d399a08e876f9097c3', '61857c9da1c4be4a6af0d4b8ad93c461', '女', 'li24', -100, 'li24');
delete FROM `role_permission_t`;
INSERT INTO `role_permission_t`(rid,pid) VALUES (1, 1);
INSERT INTO `role_permission_t`(rid,pid) VALUES (2, 1);
INSERT INTO `role_permission_t`(rid,pid) VALUES (3, 2);
INSERT INTO `role_permission_t`(rid,pid) VALUES (2, 3);
INSERT INTO `role_permission_t`(rid,pid) VALUES (3, 1);
INSERT INTO `role_permission_t`(rid,pid) VALUES (3, 3);
INSERT INTO `role_permission_t`(rid,pid) VALUES (3, 4);
INSERT INTO `role_permission_t`(rid,pid) VALUES (2, 4);
DELETE FROM user_role_t;
INSERT INTO `user_role_t`(uid,rid) VALUES (1, 3);
INSERT INTO `user_role_t`(uid,rid) VALUES (2, 2);
INSERT INTO `user_role_t`(uid,rid) VALUES (3, 1);
