create table question(
idQ bigint auto_increment,
question varchar(255),
primary key(idQ)
);

create table reponse(
idR bigint auto_increment,
idQ bigint,
reponseQ varchar(255),
primary key(idR),
foreign key(idQ) references question(idQ)
);

create table picture(
idP bigint auto_increment,
idQ bigint,
imageLocation varchar(255),
primary key(idP),
foreign key (idQ) references question(idQ)
);