.read /u1/cs348/public/sqlite/createschema.sql
.read /u1/cs348/public/sqlite/populate.sql

/*
	Question: 3
	The trategy behind the formulation of my answer:
        - By grouping distinct classes via their CNO,TERM,SECTION, I created a table
          that would hold the class information with each of their average marks.
          Since class information within enrollment are distinct and unique, we can also
          Count how many of them(ie student that enrolled in the course) are there and the
          Class Average.
          Thus, CLASS_10_AVG_85 provides all classes that have more than 10 enrolled and
          Avearge of 85. (CLASS_10_AVG_85)
        - We then wanted to find the professor that has association with the class that
          satisfied by the assignment question, thus inner joinning CLASS_10_AVG_85 with
          Class will provide the INSTRUCTOR field which I then can use it to find the
          name of the Professor (WITH_PROF)
        - Last, output the inner join of the table above with instructor and match via
          the EID from instructor and get the PNAME
	Reasonable assumptions:
*/

-- Class of at least 10 with average above 85
CREATE TABLE CLASS_10_AVG_85 AS
SELECT DISTINCT CNO,TERM,SECTION,AVG(MARK) AS Average_Mark 
FROM Enrollment
GROUP BY CNO,SECTION,TERM 
      HAVING COUNT(*)>=10 AND AVG(MARK)>85;

-- With Prof
CREATE TABLE WITH_PROF AS
SELECT DISTINCT INSTRUCTOR,CLASS_10_AVG_85.CNO,CLASS_10_AVG_85.TERM,CLASS_10_AVG_85.SECTION,Average_Mark
FROM CLASS_10_AVG_85 INNER JOIN Class on
    CLASS_10_AVG_85.CNO=Class.CNO AND
    CLASS_10_AVG_85.TERM=Class.TERM AND
    CLASS_10_AVG_85.SECTION=Class.SECTION
ORDER BY INSTRUCTOR,Class.CNO;

SELECT DISTINCT PNAME,DEPT,CNO,TERM,SECTION,Average_Mark
FROM WITH_PROF INNER JOIN Professor on WITH_PROF.INSTRUCTOR=PROFESSOR.EID
ORDER BY PNAME,DEPT,CNO;

/*OUTPUT
ANN|Computer Science|CS213|F11|2|85.9166666666667
BARBARA|Computer Science|CS137|S02|3|86.8461538461538
BARBARA|Computer Science|CS137|W09|3|85.6
BARBARA|Computer Science|CS213|S04|1|86.0
BARBARA|Computer Science|CS213|W00|2|85.7
BARBARA|Computer Science|CS483|S13|2|85.0833333333333
BETH|Physics|PHYS457|F01|2|86.6428571428571
CODY|Computer Science|CS138|F00|3|85.6
DEBORA|Physics|PHYS451|F10|1|86.8333333333333
DUSTIN|Physics|PHYS214|W12|2|86.1818181818182
DUSTIN|Physics|PHYS271|W13|3|86.7777777777778
DUSTIN|Physics|PHYS477|W11|3|86.0
HOLLIS|Computer Science|CS484|F06|2|86.1
JACKLYN|Physics|PHYS147|W07|2|86.0909090909091
JERALD|Computer Science|CS138|F10|2|85.0769230769231
JERALD|Computer Science|CS489|W13|1|85.5
JESSICA|Mathematics|MATH226|F12|1|85.5454545454546
JESSICA|Mathematics|MATH285|W03|3|87.8
JESSICA|Mathematics|MATH358|W02|1|85.0833333333333
JESSICA|Mathematics|MATH399|F09|1|85.1
JESSICA|Mathematics|MATH439|F12|1|85.4
LILIA|Physics|PHYS189|F11|2|86.9166666666667
LINA|Computer Science|CS241|W04|2|85.8
MORRIS|Physics|PHYS428|S13|3|85.1666666666667
MORRIS|Physics|PHYS442|S03|1|85.3
PETER|Mathematics|MATH358|W00|1|86.25
PETER|Mathematics|MATH452|F08|3|86.7272727272727
RODRIGO|Computer Science|CS135|S11|2|86.0
RODRIGO|Computer Science|CS446|W03|2|87.7
SHAUNA|Physics|PHYS113|S10|3|85.6
STUART|Physics|PHYS289|F09|2|85.5
STUART|Physics|PHYS414|W04|2|86.2
ULYSSES|Physics|PHYS312|F11|1|85.0833333333333
ULYSSES|Physics|PHYS331|S05|1|86.7272727272727
ULYSSES|Physics|PHYS363|S13|2|88.0909090909091
*/
