drop table if exists raleigh_meeting;
create table raleigh_meeting (
  id serial primary key,
  meeting_time varchar(10),
  group_name varchar(100),
  location varchar(100),
  address varchar(100),
  city varchar(50),
  latitude varchar(20),
  longitude varchar(20)
);