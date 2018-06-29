import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Search {
	
	private final Scanner in = new Scanner(System.in);
	
	public Search() {
	}
	
	public void searchOption() {
		System.out.println("Search Option---------------------------------------");
		System.out.println("#1 Search customer by name");
		System.out.println("#2 Search customer by phone");
		System.out.println("#3 Search customer by car number");
		System.out.println("#4 Search bill by bill number");
		System.out.println("#5 Back to Menu");
		System.out.println("----------------------------------------------------");
		System.out.print("Enter option: ");
		int option = in.nextInt();
		
		if(option == 1 || option == 2 || option == 3 || option == 4) {
			searchCus(option);
		}else if(option == 5) {
			//Do nothing
		}else {
			System.out.println("Wrong input. Retry.");
			searchOption();
		}
	}
	
	public void searchCus(int num) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = null;
			if(num == 1) {
				System.out.print("Enter first name: ");
				String fname = in.next();
				System.out.print("Enter last name: ");
				String lname = in.next();
				rs = st.executeQuery("select * from customer where fname = '" + fname
						+ "' and lname = '" + lname + "'");
			}else if(num == 2) {
				System.out.print("Enter phone number: ");
				String phoneN = in.next();
				rs = st.executeQuery("select * from customer where phonenum = " + phoneN);
			}else if(num == 3) {
				System.out.print("Enter car number: ");
				String cnum = in.next();
				rs = st.executeQuery("select * from customer where cnumber = '" + cnum + "'");
			}else{
				System.out.print("Enter bill number: ");
				int cnum = in.nextInt();
				rs = st.executeQuery("select * from bill where billnum = " + Integer.toString(cnum) );
			}
			if(!rs.next()) {
				System.out.println("No Match Data. Retry.");
				searchOption();
			}else {
				do {
					System.out.println("----------------------------------------------------");
					if(num == 1 || num == 2 || num == 3) {
						System.out.println("Bill Number: " + rs.getInt("billnum"));
						System.out.println("Name: " + rs.getString("lname") + ", " + rs.getString("fname"));
						System.out.println("Car color: " + rs.getString("color"));
						System.out.println("Car Brand: " + rs.getString("brand"));
						System.out.println("Car number: " + rs.getString("cnumber"));
						System.out.println("Phone number: " + rs.getLong("phonenum"));
						System.out.println("Spot number: " + rs.getLong("spot"));
					}else{
						System.out.println("Bill Number: " + rs.getInt("billnum"));
						System.out.println("Check In: " + rs.getTimestamp("checkin"));
						System.out.println("Check Out: " + rs.getTimestamp("checkout"));
						System.out.println("Price: " + rs.getLong("price"));
						System.out.println("Spot number: " + rs.getLong("spot"));
					}
				}while(rs.next());
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}


}
