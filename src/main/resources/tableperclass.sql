use inheritancemapping;

create table rectangle(
	id int PRIMARY KEY NOT NULL,
	width decimal(8,2),
	height decimal(8,2)
);

create table circle(
	id int PRIMARY KEY NOT NULL,
	radius decimal(8,2)
);

create table id_generator(
	gen_key varchar(20) PRIMARY KEY,
	gen_value int
);

select * from rectangle;
select * from circle;
