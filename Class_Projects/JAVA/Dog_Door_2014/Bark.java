import java.util.*;
import java.util.Scanner;
import java.io.*;

public class Bark extends BarkRecognizer{

    private String inputb;
    private int count = 0;
    private int numofdog;

    public Bark(){
    }

    public Bark(String a){
	inputb = a;
    }

    public void SetUpS(){
	Scanner in = new Scanner(System.in);
	System.out.print("How many dogs you have? ");
	numofdog = in.nextInt();
	in.nextLine();
	super.NumOfD(numofdog);
	super.SetUpSound();
    }

    public void Count(){
	String filenn = "dog";
	try{
	    FileReader fr = new FileReader(filenn);
	    BufferedReader br = new BufferedReader(fr);
	    String word = null;
	    while((word = br.readLine())!= null){
	        if(inputb.equals(word)){
		    count++;
	        }
	    }
	} catch (FileNotFoundException e){
	} catch (IOException ex){
	}	
    }	

    public void CompareS(){
	if(count > 0){
	    System.out.println("Your dog just barked.");
	    TimerF tc = new TimerF(5);
	    tc.CheckT();
	}else if(inputb.equals(super.scratchings())){
	    System.out.println("Your dog just scratched the door.");
	    System.out.println("The dog door is open for 5 seconds.");
	    TimerF tc = new TimerF(5);
	    tc.CheckT();
	}else {
	    System.out.println("It is not your dog's bark sound.");
	}
    }

    
}