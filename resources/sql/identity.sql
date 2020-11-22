-- 选中数据库
use datapool;

-- 如果存在就删除
drop table if exists identity;

-- 创建中国政府网身份认证表
create table identity
(
    -- id自增长
    id      int(11) primary key auto_increment,
    num     char(18)  not null,
    truly   boolean,
    message text,
    time    timestamp not null default current_timestamp on update current_timestamp,
    unique index num (num)
);
