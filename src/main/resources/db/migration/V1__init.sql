
CREATE TABLE user (
                        ID INT NOT NULL AUTO_INCREMENT,
                        NAME VARCHAR(100) not null,
                        primary key (`ID`)
);


CREATE TABLE room (
                       ID INT AUTO_INCREMENT NOT NULL,
                       NUMBER INT not null,
                       STATUS ENUM('READY_TO_RESERVE', 'INTERDICTED') NOT NULL,
                       primary key (`ID`)
);

CREATE TABLE reservation (
                      ID INT AUTO_INCREMENT NOT NULL,
                      NUMBER INT not null,
                      DATE_CHECK_IN TIMESTAMP not null,
                      DATE_CHECK_OUT TIMESTAMP not null,
                      USER_ID INT not null,
                      ROOM_ID INT not null,
                      STATUS ENUM('CANCELED', 'FINALIZED', 'RESERVED') NOT NULL,
                      primary key (`ID`),
                      FOREIGN KEY (USER_ID) REFERENCES user(ID),
                      FOREIGN KEY (ROOM_ID) REFERENCES user(ID)
);




