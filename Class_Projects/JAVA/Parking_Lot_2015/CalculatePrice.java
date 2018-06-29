public class CalculatePrice <T extends Comparable<T>>{

    private double total;
    private double inTime;
    private double outTime;
    private double totalIncome = 0;
    private int inputn;
    private T sizen;
    private T a;
    private T b;
    private T c;

    public CalculatePrice(State state, int nn, T aa, T bb, T cc){
	//State state = new State();
	inputn = nn;
	inTime = state.ck[nn].checkIn;
	outTime = state.ck[nn].checkOut;
	a = aa;
	b =bb;
	c = cc;
	sizec();
    }

    public T sizec(){
	if(inputn >= 0 && inputn <= 19){
	    sizen = a;
	} else if (inputn >= 20 && inputn <= 69){
	    sizen = b;
	} else if (inputn >= 70 && inputn <= 99){
	    sizen = c;
	}
	return sizen;
    }

    public double calculateR(){
	if(sizen == a){
	    total = ((double)(outTime - inTime)/3600000)*25;
	}else if(sizen == b){
	    total = ((double)(outTime - inTime)/3600000)*30;
	}else if(sizen == c){
	    total = ((double)(outTime - inTime)/3600000)*40;
	}
	return total;
    }

    public double calculateLT(){
	if(sizen == a || sizen == b){
	    total = 25 + (((double)(outTime - inTime)/3600000)-4)*5;
	}else if(sizen == c){
	    total = 30 + (((double)(outTime - inTime)/3600000)-4)*5;
	}
	return total;
    }


    public void calculateChange(double v){
	double inputm = v;
	double change;
	if(inputm > total){
	    change = inputm - total;
	    System.out.print("Change: $");
	    System.out.printf("%.2f", change);
	    System.out.println();
	} else if( inputm == total){
	    System.out.println("Thanks for your payment.");
	} else {
	    System.out.println("Sorry, you need to pay more.");
	}

	totalIncome += total;

    }

    public double income(){
	return totalIncome;
    }
}