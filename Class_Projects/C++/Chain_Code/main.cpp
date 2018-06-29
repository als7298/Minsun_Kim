#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

class ImageL{
    public:
    int numRow, numCol, maxVal, minVal;
    int** imgAry;

    ImageL(string input, string output1, string output2){
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
        file.close();

        imgAry = new int*[numRow+2];
        for(int i = 0; i < numRow+2; i++){
            imgAry[i] = new int[numCol+2];
        }

        ofstream os(output1.c_str(), ios::app);
        os << numRow << " " << numCol << " " << minVal << " " << maxVal <<"\n";
        ofstream oss(output2.c_str(), ios::app);
        oss << numRow << " " << numCol << " " << minVal << " " << maxVal <<"\n";
        os.close();
        oss.close();

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
                imgAry[row][col] = next;
            }
        }
        file.close();
    }

    void zeroFramed(){
        for(int row = 0; row < numRow+2; row++){
            for(int col = 0; col < numCol+2; col++){
                if(row == 0 || col == 0 || row == numRow+1 || col == numCol+1){
                    imgAry[row][col] = 0;
                }
            }
        }
    }                 

};

class ChainCode{     
    public:           

    class Point{      
        public:      
        int row, col; 

        Point(int r, int c){
            row = r; 
            col = c;
        }
    };

    Point *neighborCoord[8];
    Point *startP, *nextP, *currentP;
    int lastQ, nextQ, nextDir;                 
    int* nextDirTable;
    int* DirT;
    int PchainDir;
    int xxx;       

    ChainCode(){
        xxx = 0;
        startP = new Point(0,0);
        nextDirTable = new int[8];
        nextDirTable[0] = 6;
        nextDirTable[1] = 6;     
        nextDirTable[2] = 0;
        nextDirTable[3] = 0;
        nextDirTable[4] = 2;       
        nextDirTable[5] = 2;
        nextDirTable[6] = 4;
        nextDirTable[7] = 4;
        DirT = new int[8];
        DirT[0] = 4;         
        DirT[1] = 2;
        DirT[2] = 1;
        DirT[3] = 0;
        DirT[4] = 3; 
        DirT[5] = 5;
        DirT[6] = 6;
        DirT[7] = 7;  
    }

    void getChainCode(int** imgAry, int numRow, int numCol, string output1, string output2){
        int count = 0;
        ofstream o1(output1.c_str(), ios::app);
        ofstream o2(output2.c_str(), ios::app);

        for(int i = 0; i < (numRow+2); i++){
            for(int j = 0; j < (numCol+2); j++){
                if(imgAry[i][j] > 0 && startP->row == 0){
                    o1 << i << " " << j << " " << imgAry[i][j] << " ";
                    o2 << i << " " << j << " " << imgAry[i][j] << "\n";
                    startP = new Point(i, j);
                    currentP = new Point(i, j);
                }     
            }        
        }             
        lastQ = 4;
        do{                 
            nextQ = (lastQ+1)%8;
            PchainDir = findNextP(imgAry, currentP, nextQ);
            o1 << PchainDir << " ";
            if(count < 14){
                o2 << PchainDir << " ";
                count++;    
            }else {                  
                o2 << PchainDir  << " \n";     
                count = 0;
            } 
            lastQ = nextDirTable[PchainDir];
            currentP = nextP;
        }while (currentP->row != startP->row || currentP->col != startP->col);

        o1.close();
        o2.close();             

    }

    void loadNeighborsCoord(Point *cp){
        int r = cp->row;    
        int c = cp->col;           
        int cnum = 0;       
        for(int i = r-1; i <= r+1; i++){
            for(int j = c-1; j <= c+1; j++){
                if( i == r && j == c){
                }else{       
                    neighborCoord[cnum] = new Point(i, j);
                    cnum++;
                }   
            }        
        }           
    }               

    int findNextP(int** imgAry, Point *cp, int nQ){
        loadNeighborsCoord(cp);
        int chainDir = getChainDir(imgAry, cp, nQ);
        nextP = neighborCoord[DirT[chainDir]];
        return chainDir;
    }

    int getChainDir(int** imgAry, Point *cp, int nQ){
        int cnum = 0;
        int i = nQ;
        int res = 0;
        while(cnum != 8){
            if(i > 7){
                i = 0;
            }         
            int p = DirT[i];
            int num = imgAry[neighborCoord[p]->row][neighborCoord[p]->col];
            if(num > 0){   
                res = i;               
                cnum = 8;   
            }else {                  
                i++;
                cnum++;   
            } 
        }
        return res;

    }

    void prettyPrint(){
    }

};

int main(int argc, char** argv){

    ImageL *im = new ImageL(argv[1], argv[2], argv[3]);
    im -> loadImage(argv[1]);
    im -> zeroFramed();                
    ChainCode *cc = new ChainCode();
    cc -> getChainCode(im -> imgAry, im->numRow, im->numCol, argv[2], argv[3]);

}


