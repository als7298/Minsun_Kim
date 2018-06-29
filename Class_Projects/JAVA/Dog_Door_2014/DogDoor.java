import java.util.Scanner;
import java.io.*;

public class DogDoor{

    public static void main(String[] args){
	String password, inputt, barks, mydogbark;
	password = "lock";
	inputt = "";
	String endword = "done";
	String setupw = "bark";
	int inputn;

	Scanner in = new Scanner(System.in);
	
	System.out.println("What do you want to do?");
	System.out.println("\t1. Enter #1 to Open.");
	System.out.println("\t2. Enter #2 to Open and Close automatically.");
	System.out.println("\t3. Enter #3 to Close.");
	System.out.println("\t4. You can type 'lock' to lock and unlock the system.");
	System.out.println("\t5. Enter 'bark' to set you your dog barking sound and number of your dogs.");
	System.out.println("\t6. If your dog bark, the dog door will open for 5 seconds.");
	System.out.println("\t7. If your dog scratching the door, the dog door will open.(sound: drrrr)");
	System.out.println("\t8. You can type 'done' to finish the system.");

	while(!inputt.equals(endword)){
	    System.out.print("Enter: ");
	    inputt = in.nextLine();
	    
	    if(inputt.length() == 1){
		inputn = Integer.parseInt(inputt);
		Remote ob = new Remote(inputn);
		ob.door();
	    } else if (inputt.equals(password)){
		Lock lo = new Lock(password);
		lo.door();
	    } else if (!inputt.equals(endword)){
		Bark ba = new Bark(inputt);
		if(inputt.equals(setupw)){
		    ba.SetUpS();
		} else{
		    ba.Count();
		    ba.CompareS();
		}
	    }
	}

    }

}