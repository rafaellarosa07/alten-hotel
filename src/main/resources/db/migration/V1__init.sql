
CREATE TABLE USER (
                        ID INT NOT NULL AUTO_INCREMENT,
                        NAME VARCHAR(100) not null,
                        USERNAME VARCHAR(100) not null,
                        PASSWORD VARCHAR(100) not null,
                        DOCUMENT VARCHAR(20) not null,
                        primary key (`ID`)
);


CREATE TABLE ROOM (
                       ID INT AUTO_INCREMENT NOT NULL,
                       NUMBER INT not null,
                       AVAILABLE BOOLEAN NOT NULL,
                       primary key (`ID`)
);

CREATE TABLE RESERVATION (
                      ID INT AUTO_INCREMENT NOT NULL,
                      NUMBER INT not null,
                      DATE_CHECK_IN TIMESTAMP not null,
                      DATE_CHECK_OUT TIMESTAMP not null,
                      USER_ID INT not null,
                      ROOM_ID INT not null,
                      primary key (`ID`),
                      FOREIGN KEY (USER_ID) REFERENCES USER(ID),
                      FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ID)
);


