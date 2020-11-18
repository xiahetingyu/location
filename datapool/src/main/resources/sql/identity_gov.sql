-- 选中数据库
use datapool;

-- 如果存在就删除
drop table if exists identity_gov;

-- 创建中国政府网身份认证表
create table identity_gov
(
    -- id自增长
    id                 int(11) primary key auto_increment,
    num                char(18)     not null,
    name               varchar(32)  not null,
    registered         tinyint(1)            default 0,
    registered_message text,
    gov                tinyint(1)            default 0,
    gov_message        text,
    time               timestamp(0) not null default current_timestamp on update current_timestamp,
    unique index num_name (num, name)
);
