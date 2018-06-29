#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

class HeapSort{
    public:
    int rootIndex, fatherIndex, leftKidIndex, rightKidIndex, minKidIndex;
    
    HeapSort(){
        
    }
    
    void buildHeap(int *heapAry, string inputf, string outputf){
        rootIndex = 1;
        ifstream file;
        file.open(inputf.c_str());
        
        string word;
        int num;
        while(file >> word){
            stringstream geek(word);
            geek >> num;
            insertOneDataItem(heapAry,num);
            bubbleUp(heapAry, heapAry[0]);
            printHeap(heapAry, outputf);
        }
        ofstream ofs(outputf.c_str(), ios::app);
        ofs << "\n";
        ofs.close();
        
    }
    
    void deleteRoot(int *heapAry, string output1, string output2){
        while(heapAry[0] != 0){
            int data = getRoot(heapAry);
            ofstream ofs(output2.c_str(), ios::app);
            string num;
            ostringstream g;
            g << heapAry[1];
            num = g.str();
            ofs << num + "\n";
            ofs.close();
            heapAry[1] = heapAry[heapAry[0]];
            heapAry[0]--;
            bubbleDown(heapAry, 1);
            printHeap(heapAry, output1);
        }
    }
    
    void bubbleDown(int *heapAry, int fatherIndex){
        if(isLeaf(heapAry, fatherIndex)){
            return;
        }else {
            leftKidIndex = fatherIndex * 2;
            rightKidIndex = fatherIndex * 2 + 1;
            minKidIndex = findMinKidIndex(heapAry,leftKidIndex, rightKidIndex);
            if(minKidIndex < 0){
            }else {
                if(heapAry[minKidIndex] < heapAry[fatherIndex]){
                    int temp = heapAry[fatherIndex];
                    heapAry[fatherIndex] = heapAry[minKidIndex];
                    heapAry[minKidIndex] = temp;
                    bubbleDown(heapAry, minKidIndex);
                }
                
            }
            
        }
    }
    int findMinKidIndex(int heapAry[], int left, int right){
        int num = heapAry[0];
        if(left > num && right > num){
            return -1;
        }else {
            if(left > num && right <= num){
                return right;
            }else if(left <= num && right > num){
                return left;
            }else if(heapAry[left] > heapAry[right]){
                return right;
            }else{
                return left;
            }
        }
    }
    
    int getRoot(int heapAry[]){
        return heapAry[1];
    }
    
    bool isLeaf(int heapAry[], int fatherIndex){
        int left = fatherIndex*2;
        int right = fatherIndex*2 +1;
        if(left > heapAry[0] && right > heapAry[0]){
            return true;
        }else {
            return false;
        }
    }
    
    void printHeap(int heapAry[], string outputf){
        ofstream os(outputf.c_str(), ios::app);
        int c = heapAry[0]+1;
        for(int i = 1; i < c; i++){
            string num;
            ostringstream g;
            g << heapAry[i];
            num = g.str();
            os << num + " ";
        }
        os << "\n";
        os.close();
    }
        
    
    void insertOneDataItem(int *heapAry, int num){
        heapAry[0]++;
        heapAry[heapAry[0]] = num;
    }
    
    void bubbleUp(int *heapAry, int kidIndex){
        if(isRoot(kidIndex)){
            return;
        }else {
            fatherIndex = kidIndex/2;
            if(heapAry[kidIndex] < heapAry[fatherIndex]){
                int temp = heapAry[kidIndex];
                heapAry[kidIndex] = heapAry[fatherIndex];
                heapAry[fatherIndex] = temp;
            }
            bubbleUp(heapAry, fatherIndex);
        }
    }
    
    bool isRoot(int num){
        if(num == 1){
            return true;
        } else {
            return false;
        }
    }




};

int main(int argc, char** argv){
    
    ifstream file;
    file.open(argv[1]);
    if(!file.is_open()) return -1;
    
    int count = 1;
    string  word;
    while(file >> word){
        count++;
    }
    file.close();
    int heapAry[count];
    heapAry[0] = 0;
    HeapSort *hs = new HeapSort();
    hs -> buildHeap(heapAry, argv[1], argv[2]);
    hs -> deleteRoot(heapAry, argv[2], argv[3]);
    
    return 0;
    
}
