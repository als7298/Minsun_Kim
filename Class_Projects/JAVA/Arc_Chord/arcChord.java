import java.io.*;
import java.util.*;

public class arcChord {
	
	private int chordLength, numPts, P1, P2;
	boundaryPt[] ptAry;
	private double chordAry[];
	BufferedWriter bw = null;

	arcChord(int k, int numP){
		numPts = numP;
		chordLength = 2*k;
		chordAry = new double[chordLength];
		ptAry = new boundaryPt[numPts];
		setPtAry();
		for(int i = 0; i < chordLength; i++) {
			chordAry[i] = 0.0;
		}
	}
	public void setPtAry() {
		for(int i = 0; i < numPts; i++) {
			ptAry[i] = new boundaryPt(0, 0, 0, 0, 0);
		}
	}
	
	public void loadData(String input) throws IOException {
		Scanner in = null;
		try {
			in = new Scanner(new File(input));
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 5; i++) {
			in.next();
		}
		int c = 0;
		while(in.hasNext()) {
			String xVal = in.next();
			int xV = Integer.parseInt(xVal);
			String yVal = in.next();    
			int yV = Integer.parseInt(yVal);
			ptAry[c].x = xV;
			ptAry[c].y = yV;
			c++;
		}
		in.close();
		for(int i = 0; i < numPts; i++) {
			ptAry[i].maxVotes = 0;
		}
	}
	
	public void functionLoad(String input, String output1, String output2, String output3)throws IOException {
		P1 = 0;
		P2 = chordLength;
		
		int index, currPt;
		int maxIndex;
		do {
			index = 0;
			currPt = P1+1;
			double dist;
			while(index < chordLength) {
				dist = computeDistance(P1, P2, currPt);
				chordAry[index] = dist;
				index++;
				currPt = (currPt+1)%(numPts);
			}
			printChordAry(output3);
			maxIndex = findMaxDist();
			int whichIndex = (P1+maxIndex+1)%numPts;
			ptAry[whichIndex].maxVotes++;
			if(ptAry[whichIndex].maxDist < chordAry[maxIndex]) {
				ptAry[whichIndex].maxDist = chordAry[maxIndex];
			}
			//printAry1to2(P1, P2, output3);
			P1 = (P1+1) % (numPts);
			P2 = (P2+1) % (numPts); 
		}while(P2 != chordLength/2);
		printPtAry(output3);
		computeLocalMaxima();
		printInfo(output1);
		image img = new image(input);
		for(int i = 0; i < numPts; i++) {
			img.plotPt2Img(ptAry[i].x, ptAry[i].y, ptAry[i].corner);
		}
		img.prettyPrint(output2);
		
	}
	
	public void printAry1to2(int pO, int pT, String output)throws IOException {
		bw = new BufferedWriter(new FileWriter(output, true));
		bw.write("-----------------------------------------------");
		bw.newLine();
		bw.write("ptAry " + pO + " to " + pT + ": ");
		bw.newLine();
		bw.write("X / Y / maxVote / maxDist");
		for(int i = pO; i <= pT; i++) {
			bw.write(ptAry[i].x + " ");
			bw.newLine();
		}
		bw.close();
	}
	
	public void printPtAry(String output) throws IOException {
		bw = new BufferedWriter(new FileWriter(output, true));
		bw.write("-----------------------------------------------");
		bw.newLine();
		bw.write("ptAry: X / Y / MaxVote / MaxDist / Corner");
		bw.newLine();
		for(int i = 0; i < numPts; i++) {
			bw.write(ptAry[i].x + " " + ptAry[i].y + " " + ptAry[i].maxVotes + " " + ptAry[i].maxDist + " " + ptAry[i].corner);
			bw.newLine();
		}
		bw.close();
	}
	
	public double computeDistance(int p1, int p2, int cp) {
		double dis;
		double A, B, C;
		A = ptAry[p2].y - ptAry[p1].y;
		B = ptAry[p1].x - ptAry[p2].x;
		C = (ptAry[p2].x * ptAry[p1].y) - (ptAry[p1].x * ptAry[p2].y);
		dis = Math.abs( A*ptAry[cp].x + B*ptAry[cp].y + C) / Math.sqrt(A*A +B*B);
		//System.out.println(dis+ " " + A +" " + B + " " + C + " " + D);
		return dis;
	}
	
	public int findMaxDist() {
		int x = 0;
		double maxD = 0.0;
		for(int i = 0; i < chordLength; i++) {
			if(chordAry[i] > maxD) {
				maxD = chordAry[i];
				x = i;
			}
		}
		return x;
	}
	
	public void computeLocalMaxima() {
		setCorner(0, 1);
        for(int i = 0; i < numPts; i++){
        	if(i >= 2 && i < numPts-2){
        		if(ptAry[i].maxDist > 0){
        			if(ptAry[i].maxDist >= ptAry[i-2].maxDist && ptAry[i].maxDist >= ptAry[i-1].maxDist && ptAry[i].maxDist >= ptAry[i+1].maxDist && ptAry[i].maxDist >= ptAry[i+2].maxDist){
        				setCorner(i, 8);
        			}
        		}
        	}else if(i < 2){
        		if(i == 1){
        			if(ptAry[i].maxDist >= ptAry[i-1].maxDist && ptAry[i].maxDist >= ptAry[i+1].maxDist && ptAry[i].maxDist >= ptAry[i+2].maxDist && ptAry[i].maxDist >= ptAry[numPts-1].maxDist){
        				setCorner(i, 8);
        			}
        		}else if(i == 0){
        			if(ptAry[i].maxDist >= ptAry[numPts-2].maxDist && ptAry[i].maxDist >= ptAry[numPts-1].maxDist && ptAry[i].maxDist >= ptAry[i+1].maxDist && ptAry[i].maxDist >= ptAry[i+2].maxDist){
        				setCorner(i, 8);
        			}
        		}
        	}else if(i >= numPts-2){
        		if(i == numPts-2){
        			if(ptAry[i].maxDist >= ptAry[i-2].maxDist && ptAry[i].maxDist >= ptAry[i-1].maxDist && ptAry[i].maxDist >= ptAry[i+1].maxDist && ptAry[i].maxDist >= ptAry[0].maxDist){
        				setCorner(i, 8);
        			}
        		}else if(i == numPts-1){
        			if(ptAry[i].maxDist >= ptAry[0].maxDist && ptAry[i].maxDist >= ptAry[1].maxDist && ptAry[i].maxDist >= ptAry[i-2].maxDist && ptAry[i].maxDist >= ptAry[i-1].maxDist){
        				setCorner(i, 8);
        			}
        		}
        	}
            
        }
	}
	
	public void setCorner(int j, int num){
    	if(num == 1){
    		for(int i = 0; i < numPts; i++){
    			ptAry[i].corner = 1;
    		}
    	}else if(num == 8){
    		ptAry[j].corner = 8;
    	}
    }
	
	public void printInfo(String output) throws IOException{
		bw = new BufferedWriter(new FileWriter(output, true));
		for(int i = 0; i < numPts; i++) {
			bw.write(ptAry[i].x + " " + ptAry[i].y + " " + ptAry[i].corner);
			bw.newLine();
		}
		bw.close();
	}
	
	public void printChordAry(String output) throws IOException {
		bw = new BufferedWriter(new FileWriter(output, true));
		bw.write("-----------------------------------------------");
		bw.newLine();
		bw.write("chordAry: ");
		bw.newLine();
		for(int i = 0; i < chordLength; i++) {
			bw.write(chordAry[i] + " / ");
		}
		bw.newLine();
		bw.close();
	}

}
