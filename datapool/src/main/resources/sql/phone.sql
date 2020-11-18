-- 选中数据库
use datapool;

-- 如果存在就删除
drop table if exists phone;

-- 创建手机号认证表
create table phone
(
    -- id自增长
    id      int(11) primary key auto_increment,
    num     char(18)  not null,
    truly   boolean,
    message text,
    time    timestamp not null default current_timestamp on update current_timestamp,
    unique index num (num)
);
