alter table credentials add column last_password_reset_date datetime;
alter table credentials add column mobile varchar(128);
alter table credentials add column contact_person varchar(128);
alter table credentials add column address varchar(256);
