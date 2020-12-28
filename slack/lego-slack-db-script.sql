create table user(
mail varchar(255),
password varchar(255),
primary key(mail)
);

create table profile(
mail varchar(255),
idProfile varchar(255),
currentStatus varchar(255),
completeName varchar(255),
shownName varchar(255),
actualWorkPosition varchar(255),
phoneNumber varchar(255),
timezone varchar(255),
primary key(mail,idProfile),
foreign key(mail) references user(mail),
unique index idProfile_unique (idProfile)
);

create table workspace(
/*idProfile varchar(255),*/
nameWK varchar(255),
primary key(nameWK)/*,*/
/*index fk_idProfile_idx (idProfile)*/
);
/*alter table workspace add constraint fk_idProfile foreign key(idProfile) references profile(idProfile) on delete cascade on update cascade;*/

create table workspaceChannel(
nameWK varchar(255),
nameWC varchar(255),
primary key(nameWK,nameWC),
foreign key(nameWK) references workspace(nameWK)
);

create table messageChannel(
idMsg varchar(255),
nameWC varchar(255),
msgContent varchar(500),
createDate date,
updateDate date,
sender varchar(255),
primary key(idMsg)
);


create table messageDirect(
idMsg varchar(255),
idProfile varchar(255),
nameWK varchar(255),
msgContent varchar(500),
createDate date,
updateDate date,
primary key(idMsg),
foreign key(idProfile) references profile(idProfile)
); 