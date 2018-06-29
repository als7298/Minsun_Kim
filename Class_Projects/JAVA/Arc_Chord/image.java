import java.io.*;
import java.util.*;


public class image {
	private int numRow, numCol;
	private int Img[][];
	BufferedWriter bw = null;
	
	image(String input){
		
		Scanner in = null;
		try {
			in = new Scanner(new File(input));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String a = in.next();
		numRow = Integer.parseInt(a);
		String b = in.next();
		numCol = Integer.parseInt(b);
		
		in.close();
		Img = new int[numRow][numCol];
		
		for(int i = 0; i < numRow; i++) {
			for(int j = 0; j < numCol; j++) {
				Img[i][j] = 0;
			}
		}
		
	}
	
	public void plotPt2Img(int x, int y, int c) {
		Img[x][y] = c;
	}
	
	public void prettyPrint(String output) throws IOException {
		bw = new BufferedWriter(new FileWriter(output, true));
		for(int i = 0; i < numRow; i++) {
			for(int j = 0; j < numCol; j++) {
				if(Img[i][j] == 0) {
					bw.write(" ");
				}else {
					bw.write(Img[i][j] + "");
				}
			}
			bw.newLine();
		}
		bw.close();
	}

}
