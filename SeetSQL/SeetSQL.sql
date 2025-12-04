/* 创建 SeetPro 数据库 */
drop database if exists `Seetpro`;
create database `Seetpro`;

/* 选中SeetPro数据库 */
use `Seetpro`;

/* 创建用户表 */
create table if not exists `Seetpro`.`User`
(
    `Id`       int(11)      not null auto_increment primary key, /* 主键 */
    `Username` varchar(255) not null unique,/* 用户名 */
    `Password` varchar(255) not null,/* 密码 */
    `Email`    varchar(255) not null unique,/* 邮箱 */
    `Datetime` datetime     not null default current_timestamp(),/* 创建时间 */
    `Effect`   boolean      not null default true, /* 是否有效 */
    `Role`     tinyint      not null default 1 /* 是否有效 */
);

/* 添加默认数据 */
insert into `Seetpro`.`User` (`Username`, `Password`, `Email`)
values ('admin', 'admin123', 'admin@example.com'),
       ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com');


create table if not exists `Seetpro`.`User_Style`
(
    `Id`       int                                not null auto_increment primary key,
    `Userid`   int                                not null null,
    `Datetime` datetime default current_timestamp not null,
    `Name`     varchar(255) charset `Utf8mb3`     not null,
    constraint `User_Style_User_Id_Fk`
        foreign key (`Userid`) references `Seetpro`.`User` (`Id`)
);

