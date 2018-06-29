import java.io.*;
import java.security.Timestamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class CheckOut {
	
	private final Scanner in = new Scanner(System.in);
	private String billN;
	
	public CheckOut() {
	}
	
	public void removedata() {
		System.out.println("Enter 9999 for back to Menu.");
		System.out.print("Or Enter the parked location: ");
		int num = in.nextInt();
		if(num >= 0 && num < 100) {
			checkSpot(num);
		}else if(num == 9999) {
		}else {
			System.out.println("Location number is between 0 ~ 99. Retry.");
			removedata();
		}
		
	}
	
	public void checkSpot(int n) {
		int spot, vac = 1;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			String statement = "SELECT * FROM vacancy where spot=" + Integer.toString(n);
			ResultSet rs = st.executeQuery(statement);
			while(rs.next()) {
				spot = rs.getInt("spot");
				vac = rs.getInt("vac");
			}
			
			if(vac == 1) {
				//check out
				billN = "BillNumber.txt";
				Scanner in = new Scanner(new File(billN));
				String inputN = in.next();
				int billNum = Integer.parseInt(inputN);
				int newNum = billNum+1;
				in.close();
				BufferedWriter bw = new BufferedWriter(new FileWriter(billN));
				bw.write(newNum+"");
				bw.close();
				String insertb = "insert into bill (billNum) values(" + Integer.toString(billNum) + ")";
				st.executeUpdate(insertb);
				String insertc = "insert into customer (billNum) values(" + Integer.toString(billNum) + ")";
				st.executeUpdate(insertc);
				
				String updateV = "update customer set (lname, fname, color, brand, cnumber, phonenum, spot) = "
						+ "(select lastname, firstname, color, brand, cnumber, phonenum, spot from parking where spot =" 
						+ Integer.toString(n) + ") "
						+ "where billnum = " + Integer.toString(billNum);
				st.executeUpdate(updateV);
				String updateB = "update bill set(spot, checkin) = (select spot, checkIn from parking where spot ="
							+ Integer.toString(n) + ") where billnum = " + Integer.toString(billNum);
				st.executeUpdate(updateB);
				//add check out time into BILL table
				String insertTime = "update bill set checkout= ?  where billnum = " + Integer.toString(billNum);
				PreparedStatement stm = conn.prepareStatement(insertTime);
				java.sql.Timestamp  time = new java.sql.Timestamp(new java.util.Date().getTime());
				stm.setTimestamp(1, time);
				stm.executeUpdate();
				///
				calPrice(billNum, n);
				System.out.println("Bill is updated.");
				String s = "update vacancy set vac = 0 where spot = " + Integer.toString(n);
				st.executeUpdate(s);
				String ss = "update parking set lastname = null, firstname = null, color = null, brand = null, cnumber = null, phonenum = null, checkIn = null where spot = "
						+ Integer.toString(n);
				st.executeUpdate(ss);
				System.out.println("Location #" + Integer.toString(n) + " is now empty.");
			}else {
				System.out.println("Selected location is not occupied. Retry.");
				removedata();
			}
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void calPrice(int billN, int spotN) {
		int size = 0, carsize;
		double cost = 0.0;
		if(spotN >= 0 && spotN <= 19) {
			size = 1;
		}else if(spotN >= 20 && spotN <= 69) {
			size = 2;
		}else if(spotN >= 70 && spotN <= 99) {
			size = 3;
		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from price");
			
			while(rs.next()) {
				carsize = rs.getInt("carsize");
				if(carsize == size) {
					cost = rs.getDouble("cost");
				}
			}		
			rs = st.executeQuery("select checkin, checkout from bill where billnum = " + Integer.toString(billN));
			rs.next();
			java.sql.Timestamp t1 = rs.getTimestamp("checkin");
			java.sql.Timestamp t2 = rs.getTimestamp("checkout");
			
			long diff = t2.getTime() - t1.getTime();
			long diffS = diff / 1000 % 60;
			long diffM = diff / 60000 % 60;
			long diffH = diff / 3600000 % 24;
			long diffD = diff / 86400000;
			
			double finalprice = (diffD * 24 + diffH) * cost + cost*(60 * diffM + diffS) / 3600;
			String str = String.format("%.02f", finalprice);
            double newn = Double.parseDouble(str);
			rs.close();
			st.executeUpdate("update bill set price = " + str + " where billnum = " + Integer.toString(billN));
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
