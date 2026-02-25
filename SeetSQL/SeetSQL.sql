/* 创建 SeetPro 数据库 */
drop database if exists `Seetpro`;
create database `Seetpro`;

/* 选中SeetPro数据库 */
use `Seetpro`;

/* 创建用户表 */
create table if not exists `Seetpro`.`User`
(
    /* 主键，不能为0，不可重复 */
    `id`       int         not null auto_increment primary key,
    /* 名字，不能为空，可重复 */
    `name`     varchar(40) not null,
    /* 账户，不能为空，可重复，只能用字母,@,_ */
    `username` varchar(32) not null,
    constraint chk_username_format check (username REGEXP '^[a-zA-Z0-9@_]+$'),
    /* 密码，不能为空，可重复，只能用字母,@,_ */
    `password` varchar(16) not null,
    constraint chk_password_format check (username REGEXP '^[a-zA-Z0-9_]+$'),
    /* 电话，不可重复 */
    `phone`    varchar(11) not null unique,
    /* 创建时间，不能为空，可重复 */
    `datetime` datetime    not null default current_timestamp(),
    /* 是否启用，不能为空，可重复 */
    `effect`   boolean     not null default true,
    /* 角色定位ID，不能为空,可重复 */
    `roleID`   int         not null default 2
);


/* 创建用户角色表 */
create table if not exists `Seetpro`.`role`
(
    /* 主键，不能为0，不可重复 */
    `id`   int        not null auto_increment primary key, /* 主键 */
    /* 权限代号，不能为空，可重复,A1,A2,A3,...,A9,B1,B2,B3,...,B9,C1,C2以此类推 */
    `name` varchar(2) not null unique

);

/* 创建行为表 */
create table if not exists `Seetpro`.`behavior`
(
    /* 主键，不能为0，不可重复 */
    `id`   int        not null auto_increment primary key, /* 主键 */
    /* 行为代号，不能为空，可重复,A1,A2,A3,...,A9,B1,B2,B3,...,B9,C1,C2以此类推 */
    `name` varchar(2) not null unique

);


/* 添加默认数据 */
insert into `Seetpro`.`User` (`name`, `Username`, `Password`, `phone`)
values ('张3', 'admin', 'admin123', '12345612345'),
       ('张4', 'user1', 'password1', '22345612345'),
       ('张5', 'user2', 'password2', '32345612345');


create table if not exists `Seetpro`.`User_Style`
(
    `id`       int                                not null auto_increment primary key,
    `userid`   int                                not null null,
    `datetime` datetime default current_timestamp not null,
    `name`     varchar(255) charset `Utf8mb3`     not null,
    constraint `User_Style_User_Id_Fk`
        foreign key (`Userid`) references `Seetpro`.`User` (`Id`)
);

