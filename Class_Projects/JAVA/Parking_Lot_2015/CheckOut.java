import java.util.*;

public class CheckOut{

    Date date = new Date();
    private int emptys = 0;

    public CheckOut(){
    }

    public void out(State state, int a){

	int num = a;

	if(state.ck[num].getNum() == "Occupied"){
	    state.ck[num].setOut(date.toString());
	    state.ck[num].checkOut = System.currentTimeMillis();
	    state.ck[num].setNum("("+num+")");
		state.ck[num].setName("");
		state.ck[num].setCnum("");
		state.ck[num].setCbrand("");
		state.ck[num].setCcolor("");
		state.ck[num].setIn("");
		state.ck[num].setOut("");
		state.ck[num].setCsize("");
		state.ck[num].setC(0);
		System.out.println("Location #" + num + " is now empty.");
		emptys = 1;
	    }else {
		System.out.println("Location #" + num + " is empty already.");
		emptys = 2;
		
	    }

    }

    public int emptys(){
	return emptys;
    }
}