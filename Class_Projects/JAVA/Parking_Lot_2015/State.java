import java.util.*;

public class State{

    final static int totalsp = 100;
    Date date = new Date();

    public Check ck[] = new Check[totalsp];

    public State(){
	this.parkings(totalsp);
    }

    public void parkings(int a){
	for(int i = 0; i < a; i++){
	    ck[i] = new Check();
	}

	for(int i = 0; i < a; i++){
	    ck[i].setNum("("+i+")");
	}
    }

    public void totalH(int a){
	if(ck[a].getNum() == "Occupied"){
	    double t = ((double)(ck[a].checkOut - ck[a].checkIn)/360000);
	    ck[a].setC(t);
	}
    }

    public void checkSpot(int a){
	int c = 0;
	if(a == 1){
	    for(int i = 0; i <= 19; i++){
		if(ck[i].getNum() != "Occupied"){
		    c++;
		}
	    }
	} else if( a == 2){
	    for(int i = 20; i <= 69; i++){
		if(ck[i].getNum() != "Occupied"){
		    c++;
		}
	    }
	}else if(a == 3){
	    for(int i =70; i <= 99; i++){
		if(ck[i].getNum() != "Occupied"){
		    c++;
		}
	    }
	}
	System.out.println(c + " spots are available for size #" + a);
    }

}