.read /u1/cs348/public/sqlite/createschema.sql
.read /u1/cs348/public/sqlite/populate.sql

/*

    Question 5:
    The stragety behind the formulation of my answer:
        - From the start, the question asked for Student Name and Average Mark.
          Therefore, I inner joined Enrollment with Student such that Student
          Name (SNAME) can be retrieved from the table.
        - Then, I group this table via the Student Number (SNO) so that I could
          calculate their course average, as well as to count if this student
          is enrolled into at least 5 courses and each course's mark is over 80.
          By checking the MIN of the Mark within the SNO group, we can check
          the condition above with each course's mark
        - Last, output the find of the table 

*/

-- Students with 5 enrollment or more
CREATE TABLE STU_5 AS
SELECT Student.SNAME,AVG(MARK)
FROM Enrollment INNER JOIN Student ON
     Enrollment.SNO=Student.SNO
GROUP BY Enrollment.SNO
HAVING COUNT(*)>=5 AND MIN(MARK)>80;

-- Output
SELECT *
FROM STU_5;

/* OUTPUT
HRISTIAN, ROSEMARIE|86.0
ANITA, STEVE|84.0
KAREN, BENJAMIN|87.4
*/
