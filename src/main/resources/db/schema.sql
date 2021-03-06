DROP TABLE IF EXISTS CITY;
DROP TABLE IF EXISTS HOTEL;
CREATE TABLE CITY(ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR, STATE
                   VARCHAR, COUNTRY VARCHAR);
CREATE TABLE HOTEL(CITY INT, NAME VARCHAR, ADDRESS VARCHAR, ZIP VARCHAR);
INSERT INTO CITY (NAME, STATE, COUNTRY) VALUES ('San Francisco', 'CA', 'US');
INSERT INTO HOTEL
  (CITY, NAME, ADDRESS, ZIP)
VALUES
  (1, 'Conrad Treasury Place', 'William & George Streets', '4001');