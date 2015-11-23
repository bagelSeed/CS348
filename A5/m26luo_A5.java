
/*
	Assignment 5

	In this assignment, there will be one class being used (m26luo_A5) and it self will
	be executing the main function of this question.
	I've made a simple compile and run file that include the following two lines of code
	in order to this file, along with the give .jar file:
		javac -cp .:db2jcc4.jar:db2jcc_license_cu.jar m26luo_A5.java
		java -cp .:db2jcc4.jar:db2jcc_license_cu.jar m26luo_A5
	
	The program m26luo_A5 will proceed to run and it will given the user instruction to 
	what the user need to input.
	The program will run as long as the input are correct and until the user asks the
	program to terminate with the key word "exit" for any input field that it prompts

	Design and Structure:
	This class, m26luo_A5, uses simple SQL queries and data structures and manipulation
	provided with Java and JDBC:
		1) Program only uses 4 lines of simple queries, easy to understand
		2) Uses very simple and straight forward approach, easy to follow through
		3) Uses given Java data structures (Vector, String)

	The high level approach to this assigment is the following:
		- 	A department is given from the input, and it will be used to find the department
		  	of which professor that resides within. Quoting: "A section is offered by a
		  	department if it is taught by a professor from the department." Hence, the 
		  	professor of the given department will be consider as candidate for further 
		  	process
		-	By finding all professors that are within the Department, all courses that
			is/was taught by these professor can be easily extract as well
		-	We are also given Years-begin and Years-end as out bound of the classes
			that we are interested in. Assuming inclusive bound, all classes that
			started on or past Year-begin and all classes that started on or before
			Year-end will be taken consideration when we extract all enrollment of those
			particular courses taught by the professor of the given department
		- 	Similarily, we can extract the Course Name as well as each course's total Mark
			and their size via the same way as above
		- 	Now, we have all of the information that we can process the data without queries.
			On a high level, the gather information that is currently stored within a vector
			structure will be sorted via the Term (ie, the year) as well as the course #
			within each of the Term. Next, their total enrollment, Course Average, Max
			Average and Min average will be calculated within the Java program and proceed
			to output the findings right after. Each of the courses that is selected for 
			processing will be removed from the Vector, hence decrease runtime with each
			iterations and reduce the number of times each element will needed to be accessed

	Sample output:
	Please follow the instructions to construct your query
	Type 'exit' to anything of the field will terminate this program
	Setting up connections:...
	Enter Department:
	CS
	Enter Year Begin:
	1989
	Enter Year End:
	1992
	C#     | Name                                          | Enroll | #Section | Course Avg | Max Avg | Min Avg
	CS134  | Principles of Computer Science                | 46     | 1        | 82         | 82      | 82
	CS240  | Data Structures and Data Management           | 44     | 1        | 72         | 72      | 72
	CS246  | Software Abstraction and Specification        | 44     | 1        | 67         | 67      | 67
	CS342  | Concurrent Programming                        | 71     | 1        | 61         | 61      | 61
	CS134  | Principles of Computer Science                | 170    | 2        | 70         | 75      | 62
	CS240  | Data Structures and Data Management           | 148    | 3        | 62         | 66      | 61
	CS241  | Foundation of Sequential Programs             | 148    | 3        | 73         | 76      | 64
	CS246  | Software Abstraction and Specification        | 148    | 2        | 67         | 70      | 60
	CS134  | Principles of Computer Science                | 79     | 4        | 64         | 68      | 55
	CS240  | Data Structures and Data Management           | 134    | 2        | 59         | 65      | 53
	CS241  | Foundation of Sequential Programs             | 147    | 3        | 73         | 79      | 68
	CS246  | Software Abstraction and Specification        | 134    | 2        | 68         | 72      | 63
	CS342  | Concurrent Programming                        | 148    | 1        | 72         | 72      | 72
	CS134  | Principles of Computer Science                | 25     | 1        | 63         | 63      | 63
	CS240  | Data Structures and Data Management           | 13     | 1        | 69         | 69      | 69
	CS246  | Software Abstraction and Specification        | 13     | 1        | 42         | 42      | 42
	Enter Department:
	CS
	Enter Year Begin:
	1990
	Enter Year End:
	1993
	C#     | Name                                          | Enroll | #Section | Course Avg | Max Avg | Min Avg
	CS134  | Principles of Computer Science                | 170    | 2        | 70         | 75      | 62
	CS240  | Data Structures and Data Management           | 148    | 3        | 62         | 66      | 61
	CS241  | Foundation of Sequential Programs             | 148    | 3        | 73         | 76      | 64
	CS246  | Software Abstraction and Specification        | 148    | 2        | 67         | 70      | 60
	CS134  | Principles of Computer Science                | 79     | 4        | 64         | 68      | 55
	CS240  | Data Structures and Data Management           | 134    | 2        | 59         | 65      | 53
	CS241  | Foundation of Sequential Programs             | 147    | 3        | 73         | 79      | 68
	CS246  | Software Abstraction and Specification        | 134    | 2        | 68         | 72      | 63
	CS342  | Concurrent Programming                        | 148    | 1        | 72         | 72      | 72
	CS134  | Principles of Computer Science                | 25     | 1        | 63         | 63      | 63
	CS240  | Data Structures and Data Management           | 13     | 1        | 69         | 69      | 69
	CS246  | Software Abstraction and Specification        | 13     | 1        | 42         | 42      | 42
	CS240  | Data Structures and Data Management           | 21     | 1        | 75         | 75      | 75
	CS241  | Foundation of Sequential Programs             | 21     | 1        | 72         | 72      | 72
	CS246  | Software Abstraction and Specification        | 21     | 1        | 42         | 42      | 42
	Enter Department:
	CS
	Enter Year Begin:
	1991
	Enter Year End:
	1995
	C#     | Name                                          | Enroll | #Section | Course Avg | Max Avg | Min Avg
	CS134  | Principles of Computer Science                | 79     | 4        | 64         | 68      | 55
	CS240  | Data Structures and Data Management           | 134    | 2        | 59         | 65      | 53
	CS241  | Foundation of Sequential Programs             | 147    | 3        | 73         | 79      | 68
	CS246  | Software Abstraction and Specification        | 134    | 2        | 68         | 72      | 63
	CS342  | Concurrent Programming                        | 148    | 1        | 72         | 72      | 72
	CS134  | Principles of Computer Science                | 25     | 1        | 63         | 63      | 63
	CS240  | Data Structures and Data Management           | 13     | 1        | 69         | 69      | 69
	CS246  | Software Abstraction and Specification        | 13     | 1        | 42         | 42      | 42
	CS240  | Data Structures and Data Management           | 21     | 1        | 75         | 75      | 75
	CS241  | Foundation of Sequential Programs             | 21     | 1        | 72         | 72      | 72
	CS246  | Software Abstraction and Specification        | 21     | 1        | 42         | 42      | 42
	CS342  | Concurrent Programming                        | 26     | 1        | 63         | 63      | 63
	CS348  | Introduction to Database Management           | 48     | 2        | 75         | 76      | 74
	CS354  | Operating Systems                             | 26     | 1        | 76         | 76      | 76
	CS348  | Introduction to Database Management           | 79     | 2        | 76         | 77      | 74
	Enter Department:
*/
import java.sql.*;
import java.io.*;
import java.util.*;
import java.lang.Object;

public class m26luo_A5 {

	static final String jdbc_driver = "com.ibm.db2.jcc.DB2Driver"; 
	static final String url = "jdbc:db2://linux.student.cs.uwaterloo.ca:50002/cs348";

	static final String username = "db2guest";
	static final String password = "upKellynoisylair";

	static Connection con;

	static String department;
	static int y_begin;
	static int y_end;

	static Vector <Integer>allTerms;

	public static void main(String args[]) {
		// Insert bs
	
		System.out.println("Please follow the instructions to construct your query");
		System.out.println("Type 'exit' to anything of the field will terminate this program");
		System.out.println("Setting up connections:...");

		prompt();
		return;
	}

	public static void prompt() {

		try {
			con=DriverManager.getConnection(
				url,
				username,
				password
				);
		} catch(SQLException ex) {
			System.err.print("SQLException: " + ex.getMessage());
			System.exit(1);
		}

		Scanner cin;
		cin = new Scanner(System.in);
        
        String dept="";
        int index = 0;
        String[] questions = new String[3];
        questions[0]="Enter Department:";
        questions[1]="Enter Year Begin:";
        questions[2]="Enter Year End:";

        while (true){
        	System.out.println(questions[index]);
        	String input = cin.nextLine();
        	index++;
        	if (input.equals("exit")) {
        		System.exit(1);
        	}
        	if (index == 1) {
        		department= new String(input);
        	}
        	if (index == 2) {
        		y_begin= new Integer(Integer.parseInt(input));
        	}
        	if (index == 3) {
        		y_end= new Integer(Integer.parseInt(input));
        		index = 0;
        		allTerms = new Vector<Integer>();

        		y_begin = y_begin % 100;
        		y_end = y_end % 100;

        		sql_generator();
        		// System.exit(1);
        	}
        }
	}

	public static void sql_generator() {
		try {
			Class.forName(jdbc_driver);
		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
			System.exit(1);
		}

		try {
			Statement stmt;
			stmt = con.createStatement();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			Vector <Integer>p_vec = new Vector<Integer>();
			// CNO, TERM, SECTION, NAME, Average Mark, Size
			Vector < Vector <String> >cl_vec = new Vector<Vector<String>>();
			
			ResultSet professor = stmt.executeQuery(	"SELECT EID "+
													  	"FROM enrollment.Professor " +
													  	"WHERE enrollment.Professor.DEPT=\'" + department + "\'");
			while (professor.next()) {
				p_vec.addElement(professor.getInt("EID"));
			}
			
			// Got all class with the Dept specification
			ResultSet e_class = stmt.executeQuery(		"SELECT * FROM enrollment.Class ");
			while (e_class.next()) {
				boolean add_entry = false;
				for (int i = 0; i < p_vec.size(); i++) {
					if (e_class.getInt("INSTRUCTOR")==p_vec.get(i)) {
						if (checkYear(e_class.getString("TERM"))) {
							add_entry = true;
						}
						break;
					}
				}
				if (add_entry) {
					Vector <String>entry = new Vector<String>();
					entry.addElement(e_class.getString("CNO"));
					entry.addElement(e_class.getString("TERM"));
					entry.addElement(Integer.toString(e_class.getInt("SECTION")));
					cl_vec.addElement(entry);
				}
			}

			// Got the name of all classes
			for (int i = 0; i < cl_vec.size();) {
				// System.out.println("CNO: " + cl_vec.get(i).get(0));
				ResultSet cname = stmt.executeQuery(	"SELECT CNAME FROM enrollment.Course WHERE enrollment.Course.CNO=\'" + cl_vec.get(i).get(0) + "\'");
				while(cname.next()) {
					cl_vec.get(i).addElement(cname.getString("CNAME"));
				}

				ResultSet enroll = stmt.executeQuery(	"SELECT MARK FROM enrollment.Enrollment " + 
														"WHERE enrollment.Enrollment.CNO=\'" + cl_vec.get(i).get(0) + "\' AND " + 
																"enrollment.Enrollment.TERM=\'" + cl_vec.get(i).get(1) + "\' AND " + 
																"enrollment.Enrollment.SECTION=" + Integer.parseInt(cl_vec.get(i).get(2)));
				int total = 0;
				int size = 0;
				while(enroll.next()) {
					total += enroll.getInt("MARK");
					size++;
				}
				if (size == 0) {
					cl_vec.remove(i);
					continue;
				}
				// total = total/size;
				cl_vec.get(i).addElement(Integer.toString(total));
				cl_vec.get(i).addElement(Integer.toString(size));
				i++;
			}
			// CNO, TERM, SECTION, NAME, Total Mark, Size
			System.out.format("%-6s | %-45s | %-5s | %-5s | %-5s | %-5s | %-5s ",
										"C#",
										"Name",
										"Enroll",
										"#Section",
										"Course Avg",
										"Max Avg",
										"Min Avg");
			System.out.println();
			for (int i = 0; i < allTerms.size(); i++) {
				int curr_term = allTerms.get(i);
				Vector <Vector<String>> outputVector = new Vector<Vector<String>>();
				for (int j = 0; j < cl_vec.size();) {
					String year = cl_vec.get(j).get(1);
					if (curr_term == Integer.parseInt(year.substring(1,3))) {
						Vector<String> entry = new Vector<String>();

						// CNO, NAME, Size, Average Mark, TERM
						entry.addElement(cl_vec.get(j).get(0));
						entry.addElement(cl_vec.get(j).get(3));
						entry.addElement(cl_vec.get(j).get(5));
						entry.addElement(cl_vec.get(j).get(4));

						// Debug purpose:
						entry.addElement(cl_vec.get(j).get(1));
						outputVector.addElement(entry);
						cl_vec.remove(j);
						continue;
					}
					j++;
				}
				while(outputVector.size() != 0) {
					int index = getFirstNum(outputVector.get(0).get(0));
					String str_cno = (String)(outputVector.get(0).get(0)).substring(index,index+3);
					int minCourseCode = Integer.parseInt(str_cno);
					for (int j = 1; j < outputVector.size(); j++) {
						str_cno = (outputVector.get(j).get(0)).substring(index,index+3);
						if (minCourseCode > Integer.parseInt(str_cno)) {
							minCourseCode = Integer.parseInt(str_cno);
						}
					}

					int num_sec = 0;
					int num_enrol = 0;
					int max_avg = -1;
					int min_avg = 101;
					int total = 0;
					String cno = "", name = "", term="";

					for (int j = 0; j < outputVector.size();) {
						index = getFirstNum(outputVector.get(j).get(0));
						str_cno = (outputVector.get(j).get(0)).substring(index,index+3);
						if (minCourseCode == Integer.parseInt(str_cno)) {
							int cur_avg = Integer.parseInt(outputVector.get(j).get(3));
							total+=cur_avg;
							cur_avg /= Integer.parseInt(outputVector.get(j).get(2));
							if (cur_avg < min_avg) {
								min_avg = cur_avg;
							}
							if (cur_avg > max_avg) {
								max_avg = cur_avg;
							}
							num_enrol+=Integer.parseInt(outputVector.get(j).get(2));
							num_sec++;

							cno = outputVector.get(j).get(0);
							name = outputVector.get(j).get(1);
							term = outputVector.get(j).get(4);
							outputVector.remove(j);
							continue;
						}
						j++;
					}
					if (num_sec != 0) {
						total /= num_enrol;
					}
					System.out.format("%-6s | %-45s | %-6d | %-8d | %-10d | %-7d | %-8d ",
										cno,
										name,
										num_enrol,
										num_sec,
										total,
										max_avg,
										min_avg
										);
					System.out.println();
				}
			}
			stmt.close();
		} catch(SQLException ex) {
			System.err.print("SQLException: " + ex.getMessage());
			System.exit(1);
		}
	}

	public static boolean checkYear(String term) {
		String year = term.substring(1,3);
		boolean withinBound = false;
		if (y_begin > y_end) {
			if (Integer.parseInt(year) >= y_begin || Integer.parseInt(year) <= y_end){
				withinBound = true;
			}
		} else if (Integer.parseInt(year) >= y_begin && Integer.parseInt(year) <= y_end) {
			withinBound = true;
		}

		if (withinBound) {
			if (allTerms.size() == 0) {
				allTerms.addElement(Integer.parseInt(year));
			}
			for (int i = 0; i < allTerms.size(); i++) {
				if (allTerms.get(i) == Integer.parseInt(year)) {
					break;
				}
				if (allTerms.get(i) > Integer.parseInt(year)) {
					allTerms.add(i,Integer.parseInt(year));
					break;
				}
				if (i == allTerms.size() - 1) {
					allTerms.addElement(Integer.parseInt(year));
				}
			}
			return true;
		}
		return false;
	}

	public static int getFirstNum(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				return i;
			}
		}
		return str.length();
	}
} 


