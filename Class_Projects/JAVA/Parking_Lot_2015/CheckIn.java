import java.util.*;

public class CheckIn{

    Date date = new Date();
    private final Scanner in = new Scanner(System.in);
    private int carsize;
    private String letters;
    private String name = "";
    private String carnum = "";
    private String carbrand = "";
    private String carcolor = "";

    public CheckIn(){
    }

    public void setSize(State state){

	System.out.println("Size Guide -----------------");
	System.out.println("#1 Motorcycles");
	System.out.println("#2 Regular Car");
	System.out.println("#3 SUV");
	System.out.print("Car size: ");
	carsize = in.nextInt();

	int inputn;
	if(carsize == 1){
	    System.out.print("Choose a spot between 0~19: ");
	    inputn = in.nextInt();
	    this.cin(state,inputn, "1");
	} else if(carsize == 2){
	    System.out.print("Choose a spot between 20~69: ");
	    inputn = in.nextInt();
	    this.cin(state,inputn,"2");
	} else if(carsize == 3){
	    System.out.print("Choose a spot between 70~99: ");
	    inputn = in.nextInt();
	    this.cin(state, inputn, "3");
	}
    }

    public void cin(State state, int a, String l){
	int num = a;
	letters = l;

	if(state.ck[num].getNum() != "Occupied"){
	    //in.nextLine();
	    System.out.print("Customer's name: ");
	    name = in.next();
	    System.out.print("Car number: ");
	    carnum = in.next();
	    System.out.print("Car brand: ");
	    carbrand = in.next();
	    System.out.print("Car color: ");
	    carcolor = in.next();

	    state.ck[num].setName(name);
	    state.ck[num].setNum("Occupied");
	    state.ck[num].setCnum(carnum);
	    state.ck[num].setCbrand(carbrand);
	    state.ck[num].setCcolor(carcolor);
	    state.ck[num].setCsize(letters);
	    state.ck[num].setIn(date.toString());
	    state.ck[num].checkIn = System.currentTimeMillis();
	    state.ck[num].checkOut = System.currentTimeMillis();

	    System.out.println("Your car parked at location #" + num);
	    System.out.println("Location #" + num + " is " + state.ck[num].getNum());
	}else {
	    System.out.println("Selected location is occupied.");
	}
    }
}