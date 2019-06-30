use inheritancemapping;

create table payment(
	id int primary key not null AUTO_INCREMENT,
	value decimal(12,0)
);

create table credit_card(
	id int PRIMARY KEY NOT NULL,
	card_number varchar(20),
    foreign key (id) references payment(id)
);

create table check_(
	id int PRIMARY KEY NOT NULL,
    check_number varchar(20),
	foreign key (id) references payment(id)
);

select * from payment;
select * from credit_card;
select * from check_;