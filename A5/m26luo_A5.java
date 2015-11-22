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
					// System.out.println("CNO: " + e_class.getString("CNO") + " | Term:" + e_class.getString("TERM") + " | Prof:" + e_class.getInt("INSTRUCTOR") + " | Depart Prof: " + p_vec.get(i));
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
			// System.out.println("cl_vec.size: " + cl_vec.size());
			for (int i = 0; i < cl_vec.size();) {
				// System.out.println("CNO: " + cl_vec.get(i).get(0));
				ResultSet cname = stmt.executeQuery(	"SELECT CNAME FROM enrollment.Course WHERE enrollment.Course.CNO=\'" + cl_vec.get(i).get(0) + "\'");
				while(cname.next()) {
					// System.out.println(cname.getString("CNAME"));
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
				// System.out.println("CNO: " + cl_vec.get(i).get(0) + " | Term:" + cl_vec.get(i).get(1) + " | Section:" + cl_vec.get(i).get(2) + " | Average:" + cl_vec.get(i).get(4));
				i++;
			}



			// CNO, TERM, SECTION, NAME, Total Mark, Size

			// Ascending order on year, and course number
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
				// System.out.println("Current term: " + curr_term);
				Vector <Vector<String>> outputVector = new Vector<Vector<String>>();
				for (int j = 0; j < cl_vec.size();) {
					String year = cl_vec.get(j).get(1);
					// System.out.println("Trying term: " + Integer.parseInt(year.substring(1,3)));
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
		// System.out.println("Start Year: " + y_begin%100 + " | End Year: " + y_end%100 + ". Given Year: " + Integer.parseInt(year));

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
			// System.out.println("Return true:");
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


