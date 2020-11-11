CREATE DATABASE school;

SET GLOBAL time_zone = '+3:00';

USE school;

CREATE TABLE student (
                         ID INT(10) NOT NULL AUTO_INCREMENT,
                         FIRST_NAME VARCHAR (64) NOT NULL,
                         SECOND_NAME VARCHAR (64) NOT NULL,
                         BIRTH_DATE DATE,
                         ENTER_YEAR INT (4),
                         PRIMARY KEY (ID)
);

CREATE TABLE subject (
                         ID INT(10) NOT NULL AUTO_INCREMENT,
                         SUBJ_NAME VARCHAR (64) NOT NULL,
                         PRIMARY KEY (ID)
);

CREATE TABLE marks (
                       ID INT(10) NOT NULL AUTO_INCREMENT,
                       STUDENT_ID INT(10) NOT NULL,
                       SUBJECT_ID INT(10) NOT NULL,
                       MARK INT,
                       PRIMARY KEY (ID),
                       FOREIGN KEY (STUDENT_ID) REFERENCES student(ID),
                       FOREIGN KEY (SUBJECT_ID) REFERENCES subject(ID)
);

INSERT INTO student
(FIRST_NAME, SECOND_NAME, BIRTH_DATE, ENTER_YEAR)
VALUES ('Pavel', 'Soloviev' , '1985-03-09', 1991),
       ('Sergei', 'Pavlov', '1985-10-06', 1992),
       ('Valik', 'Durov', '1987-01-26', 1993),
       ('Ivan', 'Tolstov', '1985-11-04', 1992),
       ('Leonid', 'Serov', '1985-02-19', 1991),
       ('Ivan', 'Pushkin', '1987-05-06', 1993),
       ('Kir', 'Bulochkin', '1985-06-11', 1991),
       ('Tata', 'Bikova', '1985-03-08', 1991),
       ('Katerina', 'Dubova', '1986-04-03', 1992),
       ('Vika', 'Sergeeva', '1986-12-30', 1993);

INSERT INTO subject
(SUBJ_NAME)
VALUES ('biology'), ('chemistry'), ('math'), ('literature'), ('physics');

INSERT INTO marks
(STUDENT_ID, SUBJECT_ID, MARK)
VALUES (1, 1, 9), (1, 1, 7), (1, 1, 8), (1, 2, 5), (1, 2, 8), (1, 2, 10), (1, 3, 6), (1, 3, 4), (1, 3, 7),
       (2, 1, 7), (2, 1, 8), (2, 1, 4), (2, 2, 7), (2, 2, 6), (2, 2, 7), (2, 4, 4), (2, 4, 5), (2, 4, 4),
       (3, 1, 7), (3, 1, 7), (3, 1, 9), (3, 4, 10), (3, 4, 4), (3, 4, 8), (3, 5, 7), (3, 5, 10), (3, 5, 7),
       (4, 1, 7), (4, 1, 7), (4, 1, 5), (4, 5, 4), (4, 5, 4), (4, 5, 9), (4, 3, 9), (4, 3, 10), (4, 3, 7),
       (5, 4, 10), (5, 4, 9), (5, 4, 9), (5, 2, 10), (5, 2, 10), (5, 2, 7), (5, 3, 6), (5, 3, 8), (5, 3, 9),
       (6, 5, 7), (6, 5, 10), (6, 5, 4), (6, 2, 5), (6, 2, 9), (6, 2, 7), (6, 3, 9), (6, 3, 10), (6, 3, 10),
       (7, 4, 8), (7, 4, 7), (7, 4, 7), (7, 5, 10), (7, 5, 8), (7, 5, 8), (7, 3, 6), (7, 3, 4), (7, 3, 6),
       (8, 4, 7), (8, 4, 7), (8, 4, 4), (8, 2, 7), (8, 2, 9), (8, 2, 5), (8, 5, 9), (8, 5, 10), (8, 5, 4),
       (9, 1, 10), (9, 1, 7), (9, 1, 9), (9, 2, 5), (9, 2, 10), (9, 2, 7), (9, 3, 6), (9, 3, 8), (9, 3, 4),
       (10, 1, 7), (10, 1, 7), (10, 1, 5), (10, 2, 4), (10, 2, 9), (10, 2, 9), (10, 3, 7), (10, 3, 5), (10, 3, 10);





