
create table NOTEE 
(  
title varchar(50) not null, 
content varchar(255) not null, 
created timestamp default CURRENT_TIMESTAMP,
modified timestamp default CURRENT_TIMESTAMP,
version integer, 
primary key(title)
); 


create table HISTORY 
( 
id integer not null IDENTITY,
title varchar(50) not null,
content varchar(255) not null, 
created timestamp default CURRENT_TIMESTAMP,
modified timestamp default CURRENT_TIMESTAMP,
version integer,
change varchar(50) not null,
primary key(id)
);


