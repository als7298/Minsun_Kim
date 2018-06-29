import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Status {
	
	private final Scanner in = new Scanner(System.in);
	
	public Status() {	
	}
	
	public void checkOption() {
		System.out.println("Check Status Options--------------------------------");
		System.out.println("#1 By spot number");
		System.out.println("#2 By full name");
		System.out.println("#3 By car number");
		System.out.println("#4 Back to Menu");
		System.out.println("----------------------------------------------------");
		System.out.print("Enter number of option: ");
		int option = in.nextInt();
		if(option == 1 || option == 2 || option == 3) {
			checkStatus(option);
		}else if(option == 4) {
			//Do nothing and back to menu
		}else {
			checkOption();
		}
	}
	
	public void checkStatus(int num) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = null;
			if(num == 1) {
				System.out.print("Enter spot number: ");
				int spotN = in.nextInt();
				rs = st.executeQuery("select * from parking where spot = " + Integer.toString(spotN));
			}else if(num == 2) {
				System.out.print("Enter first name: ");
				String fname = in.next();
				System.out.print("Enter last name: ");
				String lname = in.next();
				rs = st.executeQuery("select * from parking where firstname = '" + fname
						+ "' and lastname = '" + lname + "'");
			}else if(num == 3) {
				System.out.print("Enter car number: ");
				String cnum = in.next();
				rs = st.executeQuery("select * from parking where cnumber = '" + cnum + "'");
			}else if(num == 4) {
				rs = st.executeQuery("select count(*) from vacancy where vac = 0");
			}
			while(rs.next()) {
				if(rs.getString("lastname") == null) {
					System.out.println("No data match. Retry");
					checkOption();
				}else{
					System.out.println("Spot: " + rs.getInt("spot"));
					System.out.println("Name: " + rs.getString("lastname") + ", " + rs.getString("firstname"));
					System.out.println("Car color: " + rs.getString("color"));
					System.out.println("Car Brand: " + rs.getString("brand"));
					System.out.println("Car number: " + rs.getString("cnumber"));
					System.out.println("Phone number: " + rs.getLong("phonenum"));
				}				
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
