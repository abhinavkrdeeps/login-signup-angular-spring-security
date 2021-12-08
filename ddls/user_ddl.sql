create table user(
user_id int not null auto_increment primary key,
provider_user_id varchar(100),
email varchar(100),
enabled bool,
display_name varchar(100),
created_date timestamp,
modified_date timestamp,
password varchar(1000),
provider varchar(1000)

);