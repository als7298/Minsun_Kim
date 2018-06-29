import java.util.Scanner;
import java.io.*;

public class BarkRecognizer{
    
    private String mydogb;
    private int numofd;
    private String scratching = "drrrr";
    private final Scanner in = new Scanner(System.in);

    public void NumOfD(int a){
	numofd = a;
    }

    public void SetUpSound(){
	String filen = "dog";
	try{
	    PrintWriter of = new PrintWriter(filen);
	    for(int i = 0; i < numofd; i++){
		System.out.print("Enter the bark sound of dog number#" + (i+1) + " : ");
	        mydogb = in.nextLine();
	        of.println(mydogb);
	    }
	    of.close();
	} catch (FileNotFoundException e){
	}
    }

    public String scratchings(){
	return scratching;
    }



}