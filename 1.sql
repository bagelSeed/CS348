.read /u1/cs348/public/sqlite/createschema.sql
.read /u1/cs348/public/sqlite/populate.sql

/*
	Question: 1
	The trategy behind the formulation of my answer:
		- Find a combined(Natural Joined) table that contains CNAME, CNO for output requirement
		- This table thus contains attribute that is needed for filtering
		- Filter this table via Room# and Term, hence we will have all CNAME and CNO of such attributes left
		- Observe that we also select the value from professor to check with the INSTRUCTOR
		  (Associated with the Class Entity) and Professor's EID number for its final selection
		- The output is then sorted by CNO as request by the question
	Reasonable assumptions:
		I assumed that the INSTRUCTOR Attribute represents the EID Primary key within Professor
*/

SELECT CNAME,CNO,PNAME
FROM Course NATURAL JOIN Class NATURAL JOIN Schedule, Professor
WHERE 
	TERM='F10' AND
	ROOM='RCH122' AND
	INSTRUCTOR=EID

ORDER BY CNO;
