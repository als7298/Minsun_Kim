import java.util.Scanner;

public class Lock{

    private String inp;
    private String pass;
    private int c = 0;
    private final Scanner in = new Scanner(System.in);

    public Lock(String a){
	pass = a;
    }

    public void door(){
	System.out.println("The door is locked.");
	while(c == 0){
	    System.out.print("Enter a password to unlock the door: ");
	    inp = in.nextLine();
	    if(inp.equals(pass)){
		System.out.println("The door is unlocked.");
		c++;
	    }
	}
    }

}
	