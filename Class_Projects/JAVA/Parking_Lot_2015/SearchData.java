import java.util.*;

public class SearchData{

    private final Scanner in = new Scanner(System.in);

    public SearchData(){
    }

    public void Data(State state){
	String inputd = "";
	int num = 0;

	
	    System.out.println("\t Search the data based on..");
	    System.out.println("\t#1 Car number.");
	    System.out.println("\t#2 Name of customer.");
    	    System.out.println("\t#3 Number of parked location.");
	    System.out.print("Enter the number: ");
	    num = in.nextInt();
	    in.nextLine();

	
	    if(num == 1){
	        System.out.print("Car number: ");
	        inputd = in.nextLine();
	        this.checkdata(state, 1, inputd);
	    }else if (num == 2){
	        System.out.print("Name: ");
	        inputd = in.nextLine();
	        this.checkdata(state, 2, inputd);
	    } else if (num == 3){
	        this.checkdata(state, 3, inputd);
	    }
	
    }

    public void checkdata(State state, int b, String c){
	int cc = 0;
	if(b == 1){
	    for(int i = 0; i < 100; i++){
		if(state.ck[i].getCnum().equals(c)){
		    this.getData(state, i);
		    cc++;	
		}
	    }
	    if(cc == 0){
		System.out.println("No match data.");
	    }
	} else if(b == 2){
	    for(int i = 0; i < 100; i++){
		if(state.ck[i].getName().equals(c)){
		    this.getData(state, i);
		    cc++;	
		}
	    }
	    if(cc == 0){
		System.out.println("No match data.");
	    }
	} else if(b == 3){
	    System.out.print("Enter the number of parked location: ");
	    int d = in.nextInt();
	    in.nextLine();
	    if(state.ck[d].getNum() == "Occupied"){
		this.getData(state, d);
	    } else {
		System.out.println("This location is not occupied.");
	    }
	}
    }

   public void getData(State state, int a){
	System.out.println("\nResult----------------------------------------");
	System.out.println("Name: " + state.ck[a].getName());
	System.out.println("Car number: " + state.ck[a].getCnum());
	System.out.println("Car brand: " + state.ck[a].getCbrand());
	System.out.println("Car color: " + state.ck[a].getCcolor());
	System.out.println("Check in time: " + state.ck[a].getIn());
    }

}