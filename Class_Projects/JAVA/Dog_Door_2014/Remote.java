public class Remote{

    private int inputn;

    public Remote(int a){
	inputn = a;
    }

    public void door(){
	if(inputn == 1){
	    System.out.println("The dog door is open.");
	}else if(inputn == 2){
	    System.out.println("The dog door is open.\nThe dog door will close automatically in 5 seconds.");
	    TimerF tc = new TimerF(5);
	    tc.CheckT();
	}else {
	    System.out.println("The dog door is close");
	}
    }
}