
CREATE TABLE USER (
                        ID INT NOT NULL AUTO_INCREMENT,
                        NAME VARCHAR(100) not null,
                        USERNAME VARCHAR(100) not null,
                        PASSWORD VARCHAR(100) not null,
                        DOCUMENT VARCHAR(20) not null,
                        primary key (`ID`)
);


CREATE TABLE HOTEL (
                        ID INT AUTO_INCREMENT NOT NULL,
                        NAME VARCHAR(100) not null,
                        ADDRESS VARCHAR(20) not null,
                        primary key (`ID`)
);
