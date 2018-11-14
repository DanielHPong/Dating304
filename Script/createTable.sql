drop table customer cascade constraints;
drop table personality cascade constraints;
drop table match cascade constraints;
drop table content cascade constraints;
drop table image_log cascade constraints;
drop table personality_match cascade constraints;
drop table premium_customer cascade constraints;
drop table payment_info cascade constraints;
drop table premium_package cascade constraints;
drop table purchase cascade constraints;
drop table benefits cascade constraints;
drop table premium_to_benefit cascade constraints;


create table personality
    (pId integer,
    type char (50),
    primary key (pId));

create table customer 
    (customerId integer not null,
    email char(50) not null,
    name char(50) not null,
    isActive char(1) not null,
    personalityId integer,
    primary key (customerId),
    unique(email),
    foreign key (personalityId) references personality);

create table match 
    (customer1Id integer,
    customer2Id integer,
    c1Active char(1) not null,
    c2Active char(1) not null,
    matchTime date,
    primary key (customer1Id, customer2Id),
    foreign key (customer1Id) references customer,
    foreign key (customer2Id) references customer);

create table content
    (chatId integer,
    senderId integer not null,
    receiverId integer not null,
    message char(255) not null,
    time date not null,
    primary key (chatId),
    foreign key (senderId) references customer,
    foreign key (receiverId) references customer);

create table image_log
    (customerId integer,
    url char(255),
    primary key (customerId, url),
    foreign key (customerId) references customer);

create table personality_match 
    (p1Id integer,
    p2Id integer,
    rank integer not null,
    primary key (p1Id, p2Id),
    foreign key (p1Id) references personality,
    foreign key (p2Id) references personality);

create table payment_info
    (infoId integer,
    cardNo char(50) not null,
    address char(255) not null,
    primary key (infoId));

create table premium_customer
    (customerId integer,
    infoId integer,
    primary key (customerId),
    foreign key (customerId) references customer,
    foreign key (infoId) references payment_info);

create table premium_package
    (pName char(50),
    price float not null,
    primary key (pName));

create table purchase
    (customerId integer,
    infoId integer,
    packageName char(50),
    purchaseTime date not null,
    primary key (customerId, infoId, packageName),
    foreign key (customerId) references premium_customer on delete cascade,
    foreign key (infoId) references payment_info on delete cascade,
    foreign key (packageName) references premium_package on delete cascade);

create table benefits 
    (bName char(50),
    bInfo char(255) not null,
    primary key (bName));

create table premium_to_benefit
    (pName char(50),
    bName char(50),
    primary key (pName, bName),
    foreign key (pName) references premium_package on delete cascade,
    foreign key (bName) references benefits on delete cascade);

-- TODO: INSERT DATA HERE
