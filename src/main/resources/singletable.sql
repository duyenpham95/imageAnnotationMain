use inheritancemapping;

create table beauty_product(
	id int PRIMARY KEY NOT NULL AUTO_INCREMENT,
	name varchar(20),
	price decimal(8,3),
	product_type varchar(20),
	material varchar(20),
	shade varchar(20)
);

select * from beauty_product;