import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Vacancy {
	
	private final Scanner in = new Scanner(System.in);
	
	public Vacancy() {
	}
	
	public void sizeGuide() {
		System.out.println("Size Guide------------------------------------------");
		System.out.println("#1 Motorcycles");
		System.out.println("#2 Regular Car");
		System.out.println("#3 SUV");
		System.out.println("#4 Any size");
		System.out.println("#5 Back to Menu");
		System.out.println("----------------------------------------------------");
	}
	
	public void checkVacancy() {
		sizeGuide();
		System.out.print("Enter the car size: ");
		int size = in.nextInt();
		if(size == 1 || size ==2 || size == 3 || size == 4){
		    checkNum(size);
		} else if(size == 5) {
			
		} else {
			System.out.println("Wrong input. Retry.");
			checkVacancy();
		}
	}
	
	public void checkNum(int num) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = null;
			if(num == 1) {
				rs = st.executeQuery("select count(*) from (select * from vacancy where spot >= 0 and spot <= 19) where vac = 0");
			}else if(num == 2) {
				rs = st.executeQuery("select count(*) from (select * from vacancy where spot >= 20 and spot <= 69) where vac = 0");
			}else if(num == 3) {
				rs = st.executeQuery("select count(*) from (select * from vacancy where spot >= 70 and spot <= 99) where vac = 0");
			}else if(num == 4) {
				rs = st.executeQuery("select count(*) from vacancy where vac = 0");
			}
			rs.next();
			int count = rs.getInt(1);
			System.out.println("--> There are " + Integer.toString(count) + " location is empty.");
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
