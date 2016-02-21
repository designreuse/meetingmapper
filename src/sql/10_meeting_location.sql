drop table if exists meeting;
drop table if exists location;
create table location (
  id serial primary key,
  location varchar(100),
  address varchar(100),
  city varchar(50),
  latitude varchar(20),
  longitude varchar(20)
);
create table meeting (
  id serial primary key,
  location_id int4 references location (id),
  day varchar(10),
  time varchar(10),
  name varchar(100),
  type varchar(50),
  notes varchar(500)
);