import java.io.IOException;
import java.util.*;

public class Menu {
	
	private int cnum;
	private final Scanner in = new Scanner(System.in);
	CheckIn checkin = new CheckIn();
	CheckOut checkout = new CheckOut();
	Vacancy vacancy = new Vacancy();
	Status status = new Status();
	BillStatement billstatement = new BillStatement();
	Refund refund = new Refund();
	CheckIncome checkIncome = new CheckIncome();
	Search search = new Search();
	
	public Menu() {
	}
	
	public void SystemOn() throws IOException {
		cnum = 0;
		int inputNum;
		
		while(cnum != 9) {
			System.out.println("----------------------------------------------------");
			System.out.println("#1 for Check In.");
		    System.out.println("#2 for Check Out.");
		    System.out.println("#3 for Check the vacancy.");
		    System.out.println("#4 for Check the status of car.");
		    System.out.println("#5 for Statement.");
		    System.out.println("#6 for Search History.");
		    System.out.println("#7 for Refund.");
		    System.out.println("#8 for Check the Income.");
		    System.out.println("#9 for Finish the program.");
		    System.out.print("\nEnter the number: ");
		    inputNum = in.nextInt();
		    cnum = inputNum;
		    System.out.println("----------------------------------------------------");
		    if(inputNum == 1) {
		    	checkin.setData();
		    }else if(inputNum == 2) {
		    	checkout.removedata();
		    }else if(inputNum == 3) {
		    	vacancy.checkVacancy();
		    }else if(inputNum == 4) {
		    	status.checkOption();
		    }else if(inputNum == 5) {
		    	billstatement.statmentOption();
		    }else if(inputNum == 6) {
		    	search.searchOption();
		    }else if(inputNum == 7) {
		    	refund.refundOption();
		    }else if(inputNum == 8) {
		    	checkIncome.ckIncome();
		    }else if(inputNum == 9) {
		    	break;
		    }else {
		    	System.out.println("Wrong input. Retry.");
		    }
		}
		
	}

}
