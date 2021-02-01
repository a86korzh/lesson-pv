# SET GLOBAL time_zone = '+3:00';

CREATE DATABASE IF NOT EXISTS school;
USE school;

DROP TABLE IF EXISTS mark;
DROP TABLE IF EXISTS subject_with_marks;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS subject;

CREATE TABLE subject (
                         ID INT NOT NULL AUTO_INCREMENT,
                         SUBJ_NAME VARCHAR (64) NOT NULL UNIQUE,
                         PRIMARY KEY (ID)
);

CREATE TABLE student (
                         ID INT NOT NULL AUTO_INCREMENT,
                         FIRST_NAME VARCHAR (64) NOT NULL,
                         SECOND_NAME VARCHAR (64) NOT NULL,
                         BIRTH_DATE DATE,
                         ENTER_YEAR INT,
                         PRIMARY KEY (ID)
);

CREATE TABLE subject_with_marks (
                      ID INT NOT NULL AUTO_INCREMENT,
                      STUDENT_ID INT NOT NULL,
                      SUBJECT_ID INT NOT NULL,
                      PRIMARY KEY (ID),
                      CONSTRAINT FOREIGN KEY (STUDENT_ID) REFERENCES student(ID)
                                ON DELETE CASCADE ON UPDATE CASCADE,
                      CONSTRAINT FOREIGN KEY (SUBJECT_ID) REFERENCES subject(ID)
                                ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE mark (
                      ID INT NOT NULL AUTO_INCREMENT,
                      subject_with_marks_ID INT NOT NULL,
                      MARK INT,
                      PRIMARY KEY (ID),
                      CONSTRAINT FOREIGN KEY (subject_with_marks_ID) REFERENCES subject_with_marks(ID)
                          ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO subject
(SUBJ_NAME)
VALUES ('biology'), ('chemistry'), ('math'), ('literature'), ('physics');

# INSERT INTO student
# (FIRST_NAME, SECOND_NAME, BIRTH_DATE, ENTER_YEAR)
# VALUES ('Pavel', 'Soloviev' , '1985-03-09', 1991),
#        ('Sergei', 'Pavlov', '1985-10-06', 1992),
#        ('Valik', 'Durov', '1987-01-26', 1993),
#        ('Ivan', 'Tolstov', '1985-11-04', 1992),
#        ('Leonid', 'Serov', '1985-02-19', 1991),
#        ('Ivan', 'Pushkin', '1987-05-06', 1993),
#        ('Kir', 'Bulochkin', '1985-06-11', 1991),
#        ('Tata', 'Bikova', '1985-03-08', 1991),
#        ('Katerina', 'Dubova', '1986-04-03', 1992),
#        ('Vika', 'Sergeeva', '1986-12-30', 1993);

# INSERT INTO subject_with_marks
# (STUDENT_ID, SUBJECT_ID)
# VALUES (1, 1),  (1, 2), (1, 3), (2, 1), (2, 2), (2, 4), (3, 1), (3, 4), (3, 5),
#        (4, 1),  (4, 5), (4, 3), (5, 4), (5, 2), (5, 3), (6, 5), (6, 2), (6, 3),
#        (7, 4),  (7, 5), (7, 3), (8, 4), (8, 2), (8, 5), (9, 1), (9, 2), (9, 3),
#        (10, 1), (10, 2),(10, 3);
#
# INSERT INTO mark
# (subject_with_marks_ID, MARK)
# VALUES (1, 9),  (2, 7), (3, 8), (4, 5),  (5, 8), (6, 10), (7, 6), (8, 4), (9, 7), (10, 7),
#        (11, 8), (12, 4), (13, 7), (14, 6), (15, 7), (16, 4), (17, 5), (18, 4), (19, 7),  (20, 7),
#        (21, 9), (22, 10), (23, 4),  (24, 8), (25, 7), (26, 10), (27, 7), (28, 7), (29, 7), (30, 5),
#        (1, 4), (2, 4), (3, 9), (4, 9), (5, 10), (6, 7), (7, 10), (8, 9), (9, 9), (10, 10),
#        (11, 10), (12, 7), (13, 6), (14, 8), (15, 9), (16, 7), (17, 10), (18, 4), (19, 5), (20, 9),
#        (21, 7), (22, 9), (23, 10), (24, 10), (25, 8),  (26, 7), (27, 7), (28, 10), (29, 8),  (30, 8),
#        (1, 6), (2, 4), (3, 6), (4, 7), (5, 7), (6, 4), (7, 7), (8, 9), (9, 5), (10, 9),
#        (11, 10), (12, 4), (13, 10), (14, 7), (15, 9), (16, 5),  (17, 10), (18, 7), (19, 6), (20, 8),
#        (21, 4), (22, 7), (23, 7), (24, 5), (25, 4), (26, 9), (27, 9), (28, 7), (29, 5), (30, 10),
#        (1, 5),  (2, 5), (3, 5), (4, 4),  (5, 4), (6, 5), (7, 5), (8, 5), (9, 5), (10, 6),
#        (11, 3), (12, 5), (13, 6), (14, 7), (15, 8), (16, 9), (17, 1), (18, 2), (19, 3),  (20, 4),
#        (21, 5), (22, 6), (23, 7),  (24, 7), (25, 8), (26, 9), (27, 1), (28, 2), (29, 3), (30, 4),
#        (1, 5), (2, 6), (3, 7), (4, 8), (5, 9), (6, 1), (7, 2), (8, 3), (9, 4), (10, 5),
#        (11, 6), (12, 8), (13, 7), (14, 7), (15, 8), (16, 9), (17, 1), (18, 2), (19, 3), (20, 4),
#        (21, 4), (22, 5), (23, 6), (24, 7), (25, 7),  (26, 8), (27, 9), (28, 1), (29, 2),  (30, 3),
#        (1, 4), (2, 5), (3, 5), (4, 6), (5, 6), (6, 7), (7, 8), (8, 7), (9, 6), (10, 6),
#        (11, 5), (12, 3), (13, 4), (14, 5), (15, 6), (16, 7),  (17, 8), (18, 9), (19, 1), (20, 2),
#        (21, 3), (22, 4), (23, 5), (24, 6), (25, 7), (26, 8), (27, 1), (28, 2), (29, 3), (30, 4);





