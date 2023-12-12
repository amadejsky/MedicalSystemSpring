create table if not exists patient(
    id bigint auto_increment,
    name varchar2(100) not null ,
    surname varchar2(100) not null ,
    age int not null,
    description varchar2(4096) not null

);