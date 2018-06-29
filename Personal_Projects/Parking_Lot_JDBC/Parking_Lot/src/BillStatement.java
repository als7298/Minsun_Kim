import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class BillStatement {
	
	private final Scanner in = new Scanner(System.in);
	
	public BillStatement() {
	}
	
	public void statmentOption() {
		
		System.out.println("Statement Option------------------------------------");
		System.out.println("#1 Bill History");
		System.out.println("#2 Customer History");
		System.out.println("#3 Refund History");
		System.out.println("#4 Back to Menu");
		System.out.println("----------------------------------------------------");
		System.out.print("Enter the car size: ");
		int inputn = in.nextInt();
		if(inputn == 1){
		    CheckBill();
		} else if(inputn == 2){
		    CheckCustomer();
		} else if(inputn == 3){
		    CheckRefund();
		} else if(inputn == 4) {
			
		} else {
			System.out.println("Wrong input. Retry.");
			statmentOption();
		}
	}
	
	public void CheckBill() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM bill order by billnum");
			String format = "|%1$-10s|%2$-30s|%3$-30s|%4$-10s|%5$-7s|\n";
			System.out.format(format, "Bill#", "Check In", "Check Out", "Price($)", "Spot#");
			while(rs.next()) {
				System.out.format(format, Integer.toString(rs.getInt("billnum")), rs.getTimestamp("checkin") , rs.getTimestamp("checkout"), 
						Double.toString(rs.getDouble("price")), Long.toString(rs.getLong("spot")));
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CheckCustomer() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM customer order by billnum");
			String format = "|%1$-10s|%2$-10s|%3$-10s|%4$-7s|%5$-7s|%6$-10s|%7$-12s|%8$-7s|\n";
			System.out.format(format, "Bill#", "Last Name", "First Name", "Color", "Brand", "Car Number", "Phone Number", "Spot#");
			while(rs.next()) {
				System.out.format(format, Integer.toString(rs.getInt("billnum")),rs.getString("lname"), rs.getString("fname"),  rs.getString("color"),
						rs.getString("brand"), rs.getString("cnumber"), Long.toString(rs.getLong("phonenum")), Long.toString(rs.getLong("spot")));
			}
			
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void CheckRefund() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM refund order by refundnum");
			String format = "|%1$-10s|%2$-10s|%3$-15s|\n";
			System.out.format(format, "Refund#", "Bill#", "Refund Price");
			while(rs.next()) {
				System.out.format(format, Integer.toString(rs.getInt("refundnum")), Integer.toString(rs.getInt("billnum")), Double.toString(rs.getDouble("refprice")));
			}
			
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
