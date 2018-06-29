import java.io.*;
import java.util.*;

public class Main {

	public static void main(String[] args) throws IOException {

		int K;
		int numRow, numCol, minVal, maxVal, label, numPts = 0;
		
		Scanner in = null;
		try {
			in = new Scanner(new File(args[0]));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String a = in.next();
		numRow = Integer.parseInt(a);
		String b = in.next();
		numCol = Integer.parseInt(b);
		String c = in.next();
		minVal = Integer.parseInt(c);
		String d = in.next();
		maxVal = Integer.parseInt(d);
		String x = in.next();
		label = Integer.parseInt(x);
		while(in.hasNext()) {
			in.next();
			in.next();
			numPts++;
		}
		
		in.close();

		BufferedWriter bw = new BufferedWriter(new FileWriter(args[1], true));
		bw.write(numRow + " " + numCol + " " + minVal + " " + maxVal);
		bw.newLine();
		bw.write(label+"");
		bw.newLine();
		bw.write(numPts+"");
		bw.newLine();
		bw.close();
		
		in = new Scanner(System.in);
		System.out.print("Enter the K value: ");
		K = in.nextInt();

		arcChord ac = new arcChord(K, numPts);
		ac.loadData(args[0]);
		ac.functionLoad(args[0], args[1], args[2], args[3]);
		
	}

}
