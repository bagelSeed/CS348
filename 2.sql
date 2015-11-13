.read /u1/cs348/public/sqlite/createschema.sql
.read /u1/cs348/public/sqlite/populate.sql

/*
	Question: 2
	The trategy behind the formulation of my answer:
		- I wanted to treat each types of Entities that we needed to sort as
		objects and do my queries
		- Objects include: CS Profs, None-CS Profs, Advanced CS Courses and the 
		prerequisite courses for those Advanced CS Courses
		- Tables are used to create these entities
		- Table PREREQ_ADVANCED_CS contains both Advanced CS Courses and their
		prerequsite courses that are offered at the same term 'S03'. The Advanced
		Course is also taught by CS Profs and their prereq is taught by None CS Profs
		- This table is used to get the table of the CS Prof with the course as well as
		the table of None CS Prof with their course
		- In the end, the Cartesian product of the tables above is taken and the row
		that matched with a row within PREREQ_ADVANCED_CS is outputed
	Reasonable assumptions: NONE
*/

-- CS Prof name
CREATE TABLE CSPROF AS
SELECT DISTINCT EID,PNAME
FROM Professor 
WHERE
	DEPT='Computer Science';

-- Non-CS Prof name
CREATE TABLE NON_CSPROF AS
SELECT DISTINCT EID,PNAME
FROM Professor 
WHERE
	NOT(DEPT='Computer Science');

-- Advanced CS Courses:
-- Condition, Course must have CSPROF as INSTRUCTOR
--			  As well as having an prereq relationship
CREATE TABLE ADVANCED_CS_COURSES AS
SELECT DISTINCT CNO, PNAME
FROM Class INNER JOIN CSPROF
WHERE
	EXISTS(SELECT EID
	 	   FROM CSPROF
	 	   WHERE Class.INSTRUCTOR=CSPROF.EID) AND
	EXISTS(SELECT PREREQ
		   FROM Prerequisite
		   WHERE Class.CNO=Prerequisite.CNO) AND
	Class.TERM='S03';

-- Advanced CS Courses:
-- Condition, Course must have NON_CSPROF as INSTRUCTOR
--			  As well as having S03 as the TERM value
CREATE TABLE PREREQ_ADVANCED_CS AS
SELECT DISTINCT ADVANCED_CS_COURSES.CNO, PREREQ
FROM ADVANCED_CS_COURSES INNER JOIN Prerequisite ON ADVANCED_CS_COURSES.CNO=Prerequisite.CNO
WHERE
	EXISTS(SELECT TERM
		   FROM Class
		   WHERE Class.CNO=Prerequisite.PREREQ AND
		   		 Class.TERM='S03' AND
		   EXISTS(SELECT EID
		   		  FROM NON_CSPROF
		   		  WHERE Class.INSTRUCTOR=NON_CSPROF.EID));

CREATE TABLE CS_ADVANCED AS
SELECT DISTINCT CNO, PNAME
FROM Class INNER JOIN CSPROF on Class.INSTRUCTOR=CSPROF.EID
WHERE
	Class.TERM='S03' AND
	EXISTS(SELECT CNO
		   FROM PREREQ_ADVANCED_CS
		   WHERE
		   		Class.CNO=PREREQ_ADVANCED_CS.CNO);

CREATE TABLE CS_PREREQ AS
SELECT DISTINCT CNO, PNAME
FROM Class INNER JOIN NON_CSPROF on Class.INSTRUCTOR=NON_CSPROF.EID
WHERE
	Class.TERM='S03' AND
	EXISTS(SELECT PREREQ
		   FROM PREREQ_ADVANCED_CS
		   WHERE
		   		Class.CNO=PREREQ_ADVANCED_CS.PREREQ);

SELECT CS_ADVANCED.CNO, CS_ADVANCED.PNAME, CS_PREREQ.CNO, CS_PREREQ.PNAME
FROM CS_ADVANCED, CS_PREREQ
WHERE
	EXISTS(SELECT PREREQ_ADVANCED_CS.CNO, PREREQ_ADVANCED_CS.PREREQ
		   FROM PREREQ_ADVANCED_CS
		   WHERE
		   		CS_ADVANCED.CNO=PREREQ_ADVANCED_CS.CNO AND
		   		CS_PREREQ.CNO=PREREQ_ADVANCED_CS.PREREQ)
ORDER BY CS_ADVANCED.CNO, CS_ADVANCED.PNAME, CS_PREREQ.CNO, CS_PREREQ.PNAME;