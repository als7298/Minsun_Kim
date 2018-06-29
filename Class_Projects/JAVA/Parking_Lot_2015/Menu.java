import java.util.Scanner;

public class Menu{

    private final Scanner in = new Scanner(System.in);
    private int inputn;
    State state = new State();
    CheckIn CheckIn = new CheckIn();
    CheckOut CheckOut = new CheckOut();
    SaveData sd = new SaveData();

    public Menu(){
    }

    public void SystemOn(){
	Choice(0);
    }

    public void Choice(int a){
	int cnum = 0;
	double income = 0;
	
	    while(cnum != 8){
	    System.out.println("\n\n");
	    System.out.println("========================================");
	    System.out.println("\t#1 for Check In.");
	    System.out.println("\t#2 for Check Out.");
	    System.out.println("\t#3 for Check the empty spot.");
	    System.out.println("\t#4 for Search your car.");
	    System.out.println("\t#5 for Manage the Data.");
	    System.out.println("\t#6 for Refund.");
	    System.out.println("\t#7 for Check the Daily Income.");
	    System.out.println("\t#8 for Finish the program.");
	    System.out.print("\nEnter the number: ");
	    inputn = in.nextInt();
	    in.nextLine();

		System.out.println("========================================");
		cnum = inputn;
		if(cnum == 1){
		    System.out.println("========================================");
		    CheckIn.setSize(state);
		} else if(cnum == 2){
		    System.out.println("========================================");
		    System.out.print("Enter the number of location: ");
		    int n = in.nextInt();
		    in.nextLine();
		    CheckOut.out(state, n);
		    if(CheckOut.emptys() == 1){
			state.totalH(n);
		        double th = state.ck[n].getCharge();
		        CalculatePrice<Integer> ob = new CalculatePrice<Integer>(state,n, 1, 2, 3); 
		        if(th < 4){
			    System.out.print("Balance: $");
			    System.out.printf("%.2f", ob.calculateR());
		        } else {
			    System.out.print("Balance: $");
			    System.out.printf("%.2f", ob.calculateLT());
		        }
		        System.out.print("\nCash: $");
		        double cash = in.nextDouble();
		        in.nextLine();
		        ob.calculateChange(cash);
			income += ob.income();
		    }
		} else if(cnum == 3){
		    System.out.println("========================================");
		    System.out.print("Enter the car size: ");
		    int b = in.nextInt();
		    in.nextLine();
		    state.checkSpot(b);
		} else if(cnum == 4){
		    System.out.println("========================================");
		    SearchData sd = new SearchData();
		    sd.Data(state);
		} else if(cnum == 5){
		    System.out.println("========================================");
		    System.out.println("\t#1 Save the Data.");
		    System.out.println("\t#2 Create the File.");
		    System.out.println("\t#3 Sorting the Data.");
		    int number = 4;
		    sd.Check(state, number);
		} else if(cnum == 6){
		    System.out.println("========================================");
		    System.out.print("Enter the balance to refund: $");
		    double refundv = in.nextDouble();
		    in.nextLine();
		    income -= refundv;
		    System.out.println("The balance of $" + refundv + " is now refunded.");
		} else if(cnum == 7){
		    System.out.println("========================================");
		    System.out.print("Daily total income: ");
		    System.out.printf("$%.2f", income);
		    System.out.println();
		} else if(cnum == 8){
		    System.out.println("========================================");
		    break;
		} else {
		    System.out.println("========================================");
		}
	    }
	
    }

}