import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CheckIncome {
	
	public CheckIncome() {
		
	}
	
	public void ckIncome() {
		String format = "|%1$-10s|%2$-30s|%3$-30s|%4$-10s|%5$-7s|\n";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "system");
			
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select sum(price) from bill");
			rs.next();
			double inc = rs.getDouble(1);
			rs.close();
			rs = st.executeQuery("select sum(refprice) from refund");
			rs.next();
			double ref = rs.getDouble(1);
			rs.close();
			System.out.println("Income: $" + inc);
			System.out.println("Refunded price: $" + ref);
			double price = inc - ref;
			System.out.println("Total : $" + price);
						
			conn.close();		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
