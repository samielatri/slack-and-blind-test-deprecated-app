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