import java.util.*;
import java.io.*;
import java.text.*;

public class SaveData{

    private String filename;
    private final Scanner in = new Scanner(System.in);
    ArrayList<Group> arr = new ArrayList<Group>();
    ArrayList<String> name = new ArrayList<String>();
    ArrayList<String> carnum = new ArrayList<String>();
    ArrayList<String> namesort = new ArrayList<String>();
    ArrayList<Integer> parkedL = new ArrayList<Integer>();

    public SaveData(){
    }

    public void Check(State state, int a){

	    System.out.print("Enter the number: ");
	    int num = in.nextInt();

	    if( num == 1 || num == 2){
		this.SaveFile(state, num);
	    } else if (num == 3){
		this.SortingF(state);
	    }

    }

    public void SaveFile(State state, int numb){

	int count = 0;
	for(int i = 0; i < 100; i++){
	    if(state.ck[i].getNum().equals("Occupied")){
	        name.add(count, state.ck[i].getName());
	        carnum.add(count, state.ck[i].getCnum());
	        parkedL.add(count, i);
		count++;
	    }
	}

	for(int i = 0; i < name.size(); i++){
	    arr.add(new Group(name.get(i), carnum.get(i), parkedL.get(i)));
	}

	System.out.println(count + " people's information have been saved.");

	if(numb == 2){
	    DateFormat d = new SimpleDateFormat("dd-MM-yyyy");
	    Date date = new Date();
	    filename = d.format(date);
	    try{
		PrintWriter of = new PrintWriter(filename);
		for(int i = 0; i < arr.size(); i++){
		    if(state.ck[i].getNum().equals("Occupied")){
			of.println(arr.get(i).getName() + "\t" + arr.get(i).getCarn() + "\t#" + arr.get(i).getNum());
		    }
		}
		of.close();
	    } catch(FileNotFoundException e){
	    }
	}

    }

    public void SortingF(State state){
	int cc = 0;
	for(int i = 0; i < 100; i++){
	    if(state.ck[i].getNum().equals("Occupied")){
		namesort.add(cc, state.ck[i].getName());
		cc++;
	    }
	}
	Collections.sort(namesort);
	DateFormat d = new SimpleDateFormat("dd-MM-yyyy");
	Date date = new Date();
	filename = d.format(date) + "_sort_version";
	try{
	    PrintWriter of = new PrintWriter(filename);
	    for(int i = 0; i < namesort.size(); i++){
		of.println(namesort.get(i));
	    }
	    of.close();
	} catch(FileNotFoundException e){
	}
	System.out.println("Sorted file saved.");
    }
}