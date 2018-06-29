#include <iostream>
#include <fstream>
#include <sstream>
#include <cmath>
using namespace std;

class morphology{
public:
	int numRowImg, numColImg, minImg, maxImg;
	int rowOrigin, colOrigin, rowFrameS, colFrameS;
	int numRowStruct, numColStruct, minStr, maxStr;
	int Row, Col;
	int** imgAry;
	int** morphAry;
	int** strucElemAry;
	int rSnum, cSnum, rEnum, cEnum;
	
	morphology(string input1, string input2){
		ifstream file;
		file.open(input1.c_str());
		int next;		
		file >> next;
		numRowImg = next;
		file >> next;
		numColImg = next;
		file >> next;
		minImg = next;
		file >> next;
		maxImg = next;
		
		file.close();
		
		ifstream file2;
		file2.open(input2.c_str());
						
		file2 >> next;
		numRowStruct = next;
		file2 >> next;
		numColStruct = next;
		file2 >> next;
		minStr = next;
		file2 >> next;
		maxStr = next;
				
		file2 >> next;
		rowOrigin = next;
		file2 >> next;
		colOrigin = next;
		
		file2.close();
		
		rowFrameS = numRowStruct/2;
		colFrameS = numColStruct/2;
		
		Row = numRowImg + rowFrameS + rowFrameS;
		Col = numColImg + colFrameS + colFrameS;
		
		imgAry = new int*[Row];
		morphAry = new int*[Row];
		strucElemAry = new int*[numRowStruct];
		
		for(int i = 0; i < Row; i++){
			imgAry[i] = new int[Col];
			morphAry[i] = new int[Col];
			if(i < numRowStruct){
				strucElemAry[i] = new int[numColStruct];
			}
		}
		
		rEnum = numRowStruct - rowOrigin-1;
		cEnum = numColStruct - colOrigin-1;
		rSnum = numRowStruct - rEnum-1;
		cSnum = numColStruct - cEnum-1;
		
	}
	
	void loadImage(string input){
		
		ifstream file;
		file.open(input.c_str());
		int next, index;
		for(int i = 0; i < 4; i++){
			file >> next;
		}
		
		for(int i = rowFrameS; i < Row - rowFrameS; i++){
			for(int j = colFrameS; j < Col - colFrameS; j++){
				file >> next;
				imgAry[i][j] = next;
			}
		}
		
		file.close();
		zeroframing();
		prettyPrint(imgAry, 1);
	}
	
	void loadStruct(string input){
		ifstream file;
		file.open(input.c_str());
		int next, index;
		for(int i = 0; i < 6; i++){
			file >> next;
		}
		
		for(int i = 0; i < numRowStruct; i++){
			for(int j = 0; j < numColStruct; j++){
				file >> next;
				strucElemAry[i][j] = next;
			}
		}
		file.close();
		prettyPrint(strucElemAry, 2);
	}
	
	void zeroframing(){
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				if (i == 0 || j == 0 || i == Row-1 || j == Col-1){
					imgAry[i][j] = 0;
				}
			}
		}
	}
	
	void dilation(string output, int num){
		if(num == 0){
			initMorphAry();
		}

		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				if(imgAry[i][j] > 0){
					int ci = 0, cj = 0;
					for(int x = i-rSnum; x <= i+rEnum; x++){
						for(int y = j-cSnum; y <= j+cEnum; y++){
							if(cj < numColStruct){
								if(strucElemAry[ci][cj] == 1){
									morphAry[x][y] = strucElemAry[ci][cj]; 
								}
							}else {
								cj = 0;
								if(strucElemAry[ci][cj] == 1){
									morphAry[x][y] = strucElemAry[ci][cj]; 
								}
							}
							cj++;
						}
						ci++;
					}
					
				}
			}
		}
		if(num == 0){
			outputResult(output, 1, morphAry);
		}
	}
	
	void erosion(string output, int num){
		if(num == 0){
			initMorphAry();
		}
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				if(imgAry[i][j] > 0){
					bool neighbor;
					neighbor = checkNeighbor(i, j);
					if(neighbor == true){
						morphAry[i][j] = 1;
					}
				}
			}
		}
		
		if(num == 0){
			outputResult(output, 2, morphAry);
		}		

	}
	
	void closing(string output){
		initMorphAry();
		dilation(output, 1);
		erosion(output, 1);
		outputResult(output, 3, morphAry);
	}
	
	void opening(string output){
		initMorphAry();
		erosion(output, 1);
		dilation(output, 1);		
		outputResult(output, 4, morphAry);
	}
	
	bool checkNeighbor(int aa, int bb){
		int** temp = new int*[numRowStruct];
		for(int x = 0; x < numRowStruct; x++){
			temp[x] = new int[numColStruct];
		}
		
		int ci = 0, cj = 0;
		bool res = true;
		
		for(int i = aa-rSnum; i <= aa+rEnum; i++){
			for(int j = bb-cSnum; j <= bb+cEnum; j++){
				if(cj >= numColStruct){
					cj = 0;
				}
				if(imgAry[i][j] != strucElemAry[ci][cj] && strucElemAry[ci][cj] == 1){
					res = false;
				}
				cj++;
			}
			ci++;
		}
		return res;
	}
	
	void initMorphAry(){
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				morphAry[i][j] = 0;
			}
		}
	}
	
	void prettyPrint(int** Ary, int num){
		if(num == 1){
			cout << "Pretty Print of Image." << endl;
			for(int i = 0; i < Row; i++){
				for(int j = 0; j < Col; j++){
					if(Ary[i][j] > 0){
						cout << Ary[i][j];
					}else {
						cout << " ";
					}
				}
				cout << endl;
			}
		}else if(num == 2){
			cout << "Pretty Print of Structure Element." << endl;
			for(int i = 0; i < numRowStruct; i++){
				for(int j = 0; j < numColStruct; j++){
					if(Ary[i][j] > 0){
						cout << Ary[i][j];
					}else {
						cout << " ";
					}
				}
				cout << endl;
			}
		}
	}
	
	void outputResult(string output, int num, int** mAry){
		ofstream of(output.c_str(), ios::app);
		if(num == 1){
			of << "Result after delation: \n";
		}else if(num == 2){
			of << "Result after erosion: \n";
		}else if(num == 3){
			of << "Result after closing: \n";
		}else if(num == 4){
			of << "Result after opening: \n";
		}
		
		for(int i = 0; i < Row; i++){
			for(int j = 0; j < Col; j++){
				if(mAry[i][j] > 0){
					of << mAry[i][j];
					of << " ";
				}else {
					of << "  ";
				}
			}
			of << "\n";
		}
		of.close();
	}
};

int main(int argc, char **argv) {
	
	morphology *mp = new morphology(argv[1], argv[2]);
	mp -> loadImage(argv[1]);
	mp -> loadStruct(argv[2]);
	mp -> dilation(argv[3], 0);
	mp -> erosion(argv[4], 0);
	mp -> closing(argv[5]);
	mp -> opening(argv[6]);
	
	return 0;
}
