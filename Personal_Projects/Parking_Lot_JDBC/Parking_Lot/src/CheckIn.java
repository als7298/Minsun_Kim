import java.util.Scanner;
import java.sql.*;

public class CheckIn {
	private final Scanner in = new Scanner(System.in);
	
	public CheckIn() {
		
	}
	
	public void sizeGuide() {
		System.out.println("Size Guide------------------------------------------");
		System.out.println("#1 Motorcycles");
		System.out.println("#2 Regular Car");
		System.out.println("#3 SUV");
		System.out.println("#4 Back to Menu");
		System.out.println("----------------------------------------------------");
	}
	
	public void setData() {
		sizeGuide();
		System.out.print("Enter the car size: ");
		int size = in.nextInt();
		int inputn = 0;
		if(size == 1){
		    System.out.print("Choose a spot between 0~19: ");
		    inputn = in.nextInt();
		    if(inputn < 0 || inputn > 19) {
		    	System.out.println("Wrong spot number. Retry.");
		    	setData();
		    }
		    dataIn(inputn);
		} else if(size == 2){
		    System.out.print("Choose a spot between 20~69: ");
		    inputn = in.nextInt();
		    if(inputn < 20 || inputn > 69) {
		    	System.out.println("Wrong spot number. Retry.");
		    	setData();
		    }
		    dataIn(inputn);
		} else if(size == 3){
		    System.out.print("Choose a spot between 70~99: ");
		    inputn = in.nextInt();
		    if(inputn < 70 || inputn > 99) {
		    	System.out.println("Wrong spot number. Retry.");
		    	setData();
		    }
		    dataIn(inputn);
		} else if(size == 4) {
			
		} else {
			System.out.println("Wrong input. Retry.");
			setData();
		}
		
	}
	
	public void dataIn(int num) {
		int spot, vac = 1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			String statement = "SELECT * FROM vacancy where spot=" + Integer.toString(num);
			ResultSet rs = st.executeQuery(statement);
			while(rs.next()) {
				spot = rs.getInt("spot");
				vac = rs.getInt("vac");
			}
			if(vac == 0) {
				System.out.println("It is empty.");
				getInfo(num);
				String s = "update vacancy set vac = 1 where spot = " + Integer.toString(num);
				st.executeUpdate(s);
			}else {
				System.out.println("Selected location is occupied. Retry");
				setData();
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void getInfo(int num) {
		System.out.print("First name: ");
		String fname = in.next();
		System.out.print("Last name: ");
		String lname = in.next();
		System.out.print("Car number: ");
	    String carnum = in.next();
	    System.out.print("Car brand: ");
	    String brand = in.next();
	    System.out.print("Car color: ");
	    String color = in.next();
	    System.out.print("Phone number:");
	    String phoneN = in.next();
	    
	    try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			String s = "update parking set lastname = '" + lname + "', firstname = '" + fname + "', color = '" + color + "', brand = '"
					+ brand + "', cnumber = '" + carnum + "', PHONENUM = " + phoneN + " where spot = " + Integer.toString(num);
			st.executeUpdate(s);
			String insertTime = "update parking set checkIn= ?  where spot = " + Integer.toString(num);
			PreparedStatement statement = conn.prepareStatement(insertTime);
			java.sql.Timestamp  time = new java.sql.Timestamp(new java.util.Date().getTime());
			statement.setTimestamp(1, time);
			statement.executeUpdate();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}	
	    
	    
	}

}
