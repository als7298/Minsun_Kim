#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

class DistanceTransform{
    
    public:
    int numRow, numCol, minVal, maxVal, newMin, newMax;
    int** ZeroFramedAry;
    int* neighborAry;
    
    DistanceTransform(string input){
        ifstream file;
        file.open(input.c_str());
        
        int next;
        file >> next;
        numRow = next;
        file >> next;
        numCol = next;
        file >> next;
        minVal = next;
        file >> next;
        maxVal = next;
        neighborAry = new int[4];
        ZeroFramedAry = new int*[numRow+2];
        for(int i = 0; i < numRow+2; i++){
            ZeroFramedAry[i] = new int[numCol+2];
        }
        file.close();
        newMin = 999;
        newMax = 0;   
    }
    
    void loadImage(string input){
        ifstream file;
        file.open(input.c_str());
        
        int next;
        for(int i = 0; i < 4; i++){
            file >> next;
        }
        for(int row = 1; row < numRow+1; row++){
            for(int col = 1; col < numCol+1; col++){
                file >> next;
                ZeroFramedAry[row][col] = next;
            }
        }
        file.close();
    }
    
    void zeroFramed(){
        for(int row = 0; row < numRow+2; row++){
            for(int col = 0; col < numCol+2; col++){
                if(row == 0 || col == 0 || row == numRow+1 || col == numCol+1){
                    ZeroFramedAry[row][col] = 0;
                }
            }
        }
    }
    
    int* loadNeighbors(int ro, int co, int pass){
        int cc = 0;
        int* tempA = new int[4];
        if(pass == 1){
            for(int i = ro-1; i <= ro; i++) {
				for(int j = co-1; j<= co+1; j++) {
					if(cc < 4) {
						tempA[cc] = ZeroFramedAry[i][j];
						cc++;
					}
				}
			}
			return tempA;
        }else if(pass == 2) {
			for(int i = ro+1; i >= ro; i--) {
				for(int j = co+1; j>= co-1; j--) {
					if(cc < 4) {
						tempA[cc] = ZeroFramedAry[i][j];
						cc++;
					}
				}
			}
			return tempA;
		}else {
			return tempA;
		}
        
    }
    
    int minN(int a, int b, int c, int d) {
		int min = a;
		if(b < min){
			min = b;
		} else if( c < min) {
			min = c;
		}else if( d < min) {
			min = d;
		}
		return min;
	}
    
    void fistPassDistance(){
        for(int i = 0; i < numRow+2; i++) {
			for(int j = 0; j < numCol+2; j++) {
				if(ZeroFramedAry[i][j] != 0) {
					int* neigh = new int[4];
					neigh = loadNeighbors(i, j, 1);
					int min = minN(neigh[0], neigh[1], neigh[2], neigh[3]);
					ZeroFramedAry[i][j] = min+1;
					updateMinMax(min+1);
				}
			}
		}
    }
    
    void secondPassDistance(){
        newMax = 0;
        newMin = 999;
        for(int i = numRow+1; i >= 0; i--) {
			for(int j= numCol+1; j >= 0; j--) {
				if(ZeroFramedAry[i][j] != 0) {
					int* neigh = new int[4];
					neigh = loadNeighbors(i, j, 2);
					int min = minN(neigh[0], neigh[1], neigh[2], neigh[3]);
					min = min+1;
					if(ZeroFramedAry[i][j] > min) {
						ZeroFramedAry[i][j] = min;
						updateMinMax(min);
					}else {
					    updateMinMax(ZeroFramedAry[i][j]);
					}
				}
			}
		}
    }
    
    void updateMinMax(int a){
        if(a > newMax){
            newMax = a;
        }
        if(a < newMin){
            newMin = a;
        }
    }
        
    void prettyPrint(string output){
        ofstream os(output.c_str(), ios::app);
        os << numRow << " " << numCol << " " << newMin << " " << newMax << "\n";
        for(int ro = 0; ro < numRow+2; ro++){
            for(int co = 0; co < numCol+2; co++){
                if(ZeroFramedAry[ro][co] == 0){
                    os << "  ";
                }else if(ZeroFramedAry[ro][co]!=0 && ZeroFramedAry[ro][co] < 10){
                    os << ZeroFramedAry[ro][co] << " ";
                }else {
                    os << ZeroFramedAry[ro][co];
                }
            }
            os << "\n";
        }
        os << "\n";
        os << "----------------------------------------------------------------------------\n";
        os.close();
    }
    
    void printPass2(string output){
        ofstream os(output.c_str(), ios::app);
        os << numRow << " " << numCol << " " << newMin << " " << newMax << "\n";
        for(int ro = 1; ro < numRow+1; ro++){
            for(int co = 1; co < numCol+1; co++){
                if(ZeroFramedAry[ro][co] < 10){
                    os << ZeroFramedAry[ro][co] << " ";
                }else {
                    os << ZeroFramedAry[ro][co];
                }
            }
            os << "\n";
        }
        os << "\n";
        os.close();
    }
    
};

int main(int argc, char** argv){
    DistanceTransform *dt = new DistanceTransform(argv[1]);
    dt -> loadImage(argv[1]);
    dt -> zeroFramed();
    dt -> fistPassDistance();
    dt -> prettyPrint(argv[3]);
    dt -> secondPassDistance();
    dt -> prettyPrint(argv[3]);
    dt -> printPass2(argv[2]);
}
