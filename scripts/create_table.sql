drop table customer cascade constraints;
drop table personality cascade constraints;
drop table match cascade constraints;
drop table content cascade constraints;
drop table image_log cascade constraints;
drop table personality_match cascade constraints;
drop table premium_customer cascade constraints;
drop table payment_info cascade constraints;
drop table payment_info_card_type cascade constraints;
drop table premium_package cascade constraints;
drop table purchase cascade constraints;
drop table benefit cascade constraints;
drop table premium_to_benefit cascade constraints;
drop view customer_bname;
drop sequence ora_d4m0b.incr_pId;
drop sequence ora_d4m0b.incr_customerId;
drop sequence ora_d4m0b.incr_chatId;
drop sequence ora_d4m0b.incr_infoId;



create sequence incr_pId
	start with 1
	increment by 1
	cache 10;

create sequence incr_customerId
	start with 1
	increment by 1
	cache 10;

create sequence incr_chatId
	start with 1
	increment by 1
	cache 10;

create sequence incr_infoId
	start with 1
	increment by 1
	cache 10;

create table personality
    (pId integer,
    type char (50),
    primary key (pId));

create table customer 
    (customerId integer not null,
    email char(50) not null,
    name char(50) not null,
    gender char(10) not null,
    isActive char(1) not null,
    personalityId integer not null,
    primary key (customerId),
    unique(email),
    foreign key (personalityId) references personality);

create table match 
    (customer1Id integer,
    customer2Id integer,
    c1Active char(1) not null,
    c2Active char(1) not null,
    primary key (customer1Id, customer2Id),
    foreign key (customer1Id) references customer,
    foreign key (customer2Id) references customer);

create table content
    (chatId integer,
    senderId integer not null,
    receiverId integer not null,
    message char(255) not null,
    time timestamp not null,
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
    primary key (infoId)
    unique (cardNo));

create table payment_info_card_type
	(cardNo char(50),
	cardType char(50) not null,
	primary key (cardNo),
	foreign key (cardNo) references payment_info (cardNo) on delete cascade);

create table premium_customer
    (customerId integer,
    infoId integer,
    primary key (customerId),
    foreign key (customerId) references customer on delete cascade,
    foreign key (infoId) references payment_info on delete cascade);

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

create table benefit
    (bName char(50),
    bInfo char(255) not null,
    primary key (bName));

create table premium_to_benefit
    (pName char(50),
    bName char(50),
    primary key (pName, bName),
    foreign key (pName) references premium_package on delete cascade,
    foreign key (bName) references benefit on delete cascade);

create view customer_bname as
	select customerId, bname
	from purchase join premium_to_benefit
	on packageName = pName;
    
	
	
insert into personality (pId, type) values
	(incr_pId.nextval, 'Introverted');
insert into personality (pId, type) values
	(incr_pId.nextval, 'Amicable');
insert into personality (pId, type) values
	(incr_pId.nextval, 'Romantic');
insert into personality (pId, type) values
	(incr_pId.nextval, 'Party Animal');

insert into personality_match (p1Id, p2Id, rank) values
	(2, 3, 1);
insert into personality_match (p1Id, p2Id, rank) values
	(2, 4, 2);
insert into personality_match (p1Id, p2Id, rank) values
	(2, 5, 3);
insert into personality_match (p1Id, p2Id, rank) values
	(3, 2, 1);
insert into personality_match (p1Id, p2Id, rank) values
	(3, 4, 3);
insert into personality_match (p1Id, p2Id, rank) values
	(3, 5, 2);
insert into personality_match (p1Id, p2Id, rank) values
	(4, 2, 2);
insert into personality_match (p1Id, p2Id, rank) values
	(4, 3, 3);
insert into personality_match (p1Id, p2Id, rank) values
	(4, 5, 1);
insert into personality_match (p1Id, p2Id, rank) values
	(5, 2, 3);
insert into personality_match (p1Id, p2Id, rank) values
	(5, 3, 2);
insert into personality_match (p1Id, p2Id, rank) values
	(5, 4, 1);

insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'yoo@naver.com', 'Jason Yoo', 'M', '1', 2);
insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'liu@naver.com', 'Ellen Liu', 'F', '1', 2);
insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'kim@naver.com', 'David Kim', 'M', '1', 3);
insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'le@naver.com', 'Daniel Le', 'M', '1', 4);
insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'knorr@naver.com', 'Ed Knorr', 'M', '1', 5);
insert into customer (customerId, email, name, gender, isActive, personalityId) values
	(incr_customerId.nextval, 'watson@naver.com', 'Emma Watson', 'F', '1', 3);

insert into match (customer1Id, customer2Id, c1Active, c2Active) values
	(2, 3, '1', '1');
insert into match (customer1Id, customer2Id, c1Active, c2Active) values
	(2, 4, '1', '1');
insert into match (customer1Id, customer2Id, c1Active, c2Active) values
	(4, 5, '1', '0');
insert into match (customer1Id, customer2Id, c1Active, c2Active) values
	(5, 7, '1', '1');
	
insert into image_log (customerId, url) values
	(2, 'google.com/img1');
insert into image_log (customerId, url) values
	(2, 'google.com/img2');
insert into image_log (customerId, url) values
	(3, 'google.com/img3');
insert into image_log (customerId, url) values
	(3, 'google.com/img4');
insert into image_log (customerId, url) values
	(4, 'google.com/img5');
insert into image_log (customerId, url) values
	(5, 'google.com/img6');

insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 2, 3, 'Hey Ellen', timestamp '2018-11-15 12:34:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 3, 2, 'Hi Jason', timestamp '2018-11-15 12:35:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 3, 2, 'What up?', timestamp '2018-11-17 12:34:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 2, 3, 'Nothing much', timestamp '2018-11-17 12:35:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 4, 5, 'Hi Daniel, this is David wyd?', timestamp '2018-11-17 12:33:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 7, 5, 'Hi Daniel, this is Emma', timestamp '2018-11-17 12:33:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 7, 5, 'Wanna hang out?', timestamp '2018-11-17 12:34:56');
insert into content (chatId, senderId, receiverId, message, time) values
	(incr_chatId.nextval, 5, 7, 'Nah I need to do 304', timestamp '2018-11-17 12:35:56');

insert into payment_info (infoId, cardNo, address) values
	(incr_infoId.nextval, '2929-2929-2929-2929', '1111 Best Avenue, BC');
insert into payment_info (infoId, cardNo, address) values
	(incr_infoId.nextval, '7777-7777-7777-7777', '2222 Absolute Best Avenue, BC');

insert into payment_info_card_type (cardNo, cardType) values
	('2929-2929-2929-2929', 'Visa');
insert into payment_info_card_type (cardNo, cardType) values
	('7777-7777-7777-7777', 'MasterCard');

insert into premium_customer (customerId, infoId) values
	(2, 2);
insert into premium_customer (customerId, infoId) values
	(6, 3);

insert into premium_package (pName, price) values
	('Gold Plan', 9.99);
insert into premium_package (pName, price) values
	('Silver Plan', 4.99);
insert into premium_package (pName, price) values
	('Bronze Plan', 0.99);

insert into purchase (customerId, infoId, packageName, purchaseTime) values
	(2, 2, 'Gold Plan', timestamp '2018-11-17 12:33:56');
insert into purchase (customerId, infoId, packageName, purchaseTime) values
	(6, 3, 'Bronze Plan', timestamp '2018-11-17 12:33:56');

insert into benefit (bName, bInfo) values
	('Extra Visibility', 'User profile display is prioritized.');
insert into benefit (bName, bInfo) values
	('Extended Matching', 'User can get matched to all users whose personality compatibility is higher than relative minimum.');
insert into benefit (bName, bInfo) values
	('Beta Testing', 'User gets to try out the newest features.');

insert into premium_to_benefit (pName, bName) values
	('Gold Plan', 'Extra Visibility');
insert into premium_to_benefit (pName, bName) values
	('Gold Plan', 'Extended Matching');
insert into premium_to_benefit (pName, bName) values
	('Gold Plan', 'Beta Testing');
insert into premium_to_benefit (pName, bName) values
	('Silver Plan', 'Extra Visibility');
insert into premium_to_benefit (pName, bName) values
	('Silver Plan', 'Extended Matching');
insert into premium_to_benefit (pName, bName) values
	('Bronze Plan', 'Extra Visibility');

