import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Refund {
	
	private final Scanner in = new Scanner(System.in);
	
	public Refund() {
		
	}
	
	public void refundOption() throws IOException {
		System.out.print("Enter the bill number: ");
		int billnum = in.nextInt();
		System.out.print("Enter the refund price: ");
		double refP = in.nextDouble();
		int refN = getRefundNum();
		boolean x = checkbillNum(billnum);
		if(x) {
			processRefund(refN, billnum, refP);
		}else {
			System.out.println("Given bill number is invalid. Back to Menu.");
		}
		
	}
	
	public int getRefundNum() throws IOException {
		String fileName = "RefundNumber.txt";
		Scanner in = new Scanner(new File(fileName));
		String inputN = in.next();
		int refNum = Integer.parseInt(inputN);
		int newNum = refNum+1;
		in.close();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		bw.write(newNum+"");
		bw.close();
		return refNum;
	}
	
	public boolean checkbillNum(int n) {
		boolean returnV = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			String statement = "select * from bill where billnum = " + Integer.toString(n);
			ResultSet rs = st.executeQuery(statement);
			if(!rs.next()) {
			}else {
				returnV = true;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return returnV;
	}
	
	public void processRefund(int num, int bnum, double ref) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			String insertb = "insert into refund (refundnum, billnum, refprice) values(" + Integer.toString(num) +
					", " + Integer.toString(bnum) + ", " + Double.toString(ref) + ")";
			st.executeUpdate(insertb);
			System.out.println("$ " + ref + " refunded.");
			conn.close();
					
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
