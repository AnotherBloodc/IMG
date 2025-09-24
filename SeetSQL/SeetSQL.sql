/* 创建 SeetPro 数据库 */
drop database if exists `Seetpro`;
create database `Seetpro`;

/* 选中SeetPro数据库 */
use `Seetpro`;

/* 创建用户表 */
create table if not exists `User`
(
    `Id`       int(11)      not null auto_increment primary key, /* 主键 */
    `Username` varchar(255) not null unique,/* 用户名 */
    `Password` varchar(255) not null,/* 密码 */
    `Email`    varchar(255) not null unique,/* 邮箱 */
    `Datetime` datetime     not null default current_timestamp(),/* 创建时间 */
    `Effect`   boolean      not null default true /* 是否有效 */
);

/* 添加默认数据 */
insert into `User` (`Username`, `Password`, `Email`, `Datetime`)
values ('admin', 'admin123', 'admin@example.com', current_timestamp),
       ('user1', 'password1', 'user1@example.com', current_timestamp),
       ('user2', 'password2', 'user2@example.com', current_timestamp);
