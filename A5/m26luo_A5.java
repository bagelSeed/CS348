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

	public static void main(String args[]) {
		// Insert bs
	
		System.out.println("Please follow the instructions to construct your query");
		System.out.println("Type 'exit' to anything of the field will terminate this program\n");

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
        		y_begin= new Integer(Integer.parseInt(input));
        		index = 0;
        		sql_generator();
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
					entry.addElement(e_class.getString("SECTION"));
					cl_vec.addElement(entry);
				}
			}

			// Got the name of all classes
			// System.out.println("cl_vec.size: " + cl_vec.size());
			for (int i = 0; i < cl_vec.size(); i++) {
				// System.out.println("CNO: " + cl_vec.get(i).get(0));
				ResultSet cname = stmt.executeQuery(	"SELECT CNAME FROM enrollment.Course WHERE enrollment.Course.CNO=\'" + cl_vec.get(i).get(0) + "\'");
				while(cname.next()) {
					// System.out.println(cname.getString("CNAME"));
					cl_vec.get(i).addElement(cname.getString("CNAME"));
				}

				ResultSet enroll = stmt.executeQuery(	"SELECT MARK FROM enrollment.Enrollment " + 
														"WHERE enrollment.Enrollment.CNO=\'" + cl_vec.get(i).get(0) + "\' AND " + 
																"enrollment.Enrollment.TERM=\'" + cl_vec.get(i).get(1) + "\' AND " + 
																"enrollment.Enrollment.SECTION=\'" + cl_vec.get(i).get(2) + "\'");
				int total = 0;
				int size = 0;
				while(enroll.next()) {
					total += enroll.getInt("MARK");
					size++;
				}
				if (size != 0) {
					total = total/size;
				}
				cl_vec.get(i).addElement(Integer.toString(total));
				cl_vec.get(i).addElement(Integer.toString(size));
				// System.out.println("CNO: " + cl_vec.get(i).get(0) + " | Term:" + cl_vec.get(i).get(1) + " | Section:" + cl_vec.get(i).get(2));
			}

			// CNO, TERM, SECTION, NAME, Average Mark, Size
			// while(cl_vec.size()) {
			// 	int totalMark = cl_vec.get(i).get(4);
			// 	int totalSize = cl_vec.get(i).get(5);
			// 	int size = 1;
			// 	for (int i = 1; i < cl_vec.size(); i++) {

			// 	}

			// }


			stmt.close();

		} catch(SQLException ex) {
			System.err.print("SQLException: " + ex.getMessage());
			System.exit(1);
		}
	}

	public static boolean checkYear(String term) {
		String year = term.substring(1,3);
		// System.out.println("Substr: " + year);
		if (Integer.parseInt(year) <= y_begin || Integer.parseInt(year) >= y_end) {
			return true;
		}
		return false;
	}
} 


