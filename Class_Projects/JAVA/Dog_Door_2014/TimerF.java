import java.util.*;

public class TimerF{

    private int timep;
    private int finalt;

    public TimerF(int a){
	timep = a;
    }

    public void CheckT(){
	Timer ti = new Timer();
	finalt = timep*1000;
	ti.schedule(new TimerT(), 0 ,finalt);
	try{
	    Thread.sleep(finalt);
	} catch (InterruptedException e){
	}
	ti.cancel();
	System.out.println("Timer is now over. The dog door is close.");
    }

}