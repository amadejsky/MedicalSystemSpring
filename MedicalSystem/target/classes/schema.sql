create table if not exists PATIENTS(
    id INT auto_increment PRIMARY KEY ,
    name varchar2(100) not null ,
    surname varchar2(100) not null ,
    age int not null,
    description varchar2(4096) not null

);