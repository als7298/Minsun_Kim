#include <iostream>
#include <fstream>
#include <sstream>
using namespace std;

class listNode{
    public:
    string chStr;
    int prob;
    listNode* next;
    listNode* left;
    listNode* right;

    listNode(string ch, int num){
        chStr = ch;
        prob = num;
        next = left = right = NULL;
    }

};
class HuffmanBinaryTree{
    public:
    listNode *root;
    listNode *oldlistHead = NULL;
    
    string fileO, fileTo, fileTh, fileFo, fileF;
    
    HuffmanBinaryTree(){
        
    }
    
    void getFileName(string a, string b, string c, string d, string e){
        fileO = a;
        fileTo = b;
        fileTh = c;
        fileFo = d;
        fileF = e;
    }
    
    void HBTree(listNode *T){
        listNode *nodeN = T;
        oldlistHead = T;
        while(nodeN->next->next != NULL){
            listNode *curr = nodeN;
            string newCh = curr->next->chStr + curr->next->next->chStr;
            int newp = curr->next->prob + curr->next->next->prob;
            listNode *newN = new listNode(newCh, newp);
            newN->left = curr->next;
            newN->right = curr->next->next;
            nodeN->next = nodeN->next->next->next;
            listNode *spot = nodeN;
            while(spot->next != NULL && spot->next->prob < newp){
                spot = spot->next;
            }
            listNode *c = spot->next;
            spot->next = newN;
            newN->next = c;
            print(nodeN);
            root = newN;
        }
        
    }
    
    void print(listNode *T){
        listNode *curr = T->next;
        ofstream os(fileF.c_str(), ios::app);
        do{
            string num;
            ostringstream sgeek;
            sgeek << curr->prob;
            num = sgeek.str();
            string a = "(" + curr->chStr + ":" + num + ")";
            os << a;
            curr = curr->next;
        }while(curr != NULL);
        os << "\n";
        os.close();
        
    }
    
    bool isLeaf(listNode *T){
        if(T->left == NULL && T->right == NULL){
            return true;
        }else {
            return false;
        }
    }
    
    void consCharC(){
        consCC(root, "");
    }
    
    void consCC(listNode *T, string code){
        ofstream os;
        os.open(fileO.c_str(), ios::app);
        if(T == NULL){
            os << "This tree is Empty";
        }else if (isLeaf(T)){
            os << T->chStr + ":" + code + "\n";
        }else{
            consCC(T->left, code+"0");
            consCC(T->right, code+"1");
        }
        os.close();
    }
    
    void inOrder(){
        inO(root);
    }
    
    void inO(listNode *T){
        ofstream of(fileTh.c_str(), ios::app);
        if( T != NULL){
            inO(T->left);
            string num;
            ostringstream sgeek;
            sgeek << T->prob;
            num = sgeek.str();
            of << T->chStr + ":" + num + "\n";
            of.close();
            inO(T->right);
        }
    }
    
    void preOrder(){
        preO(root);
    }
    
    void preO(listNode *T){
        ofstream of(fileTo.c_str(), ios::app);
        if( T != NULL){
            string num;
            ostringstream sgeek;
            sgeek << T->prob;
            num = sgeek.str();
            of << T->chStr + ":" + num + "\n";
            of.close();
            preO(T->left);
            preO(T->right);
        }
    }
    
    void postOrder(){
        postO(root);
    }
    
    void postO(listNode *T){
        ofstream of(fileFo.c_str(), ios::app);
        if( T != NULL){
            postO(T->left);
            postO(T->right);
            string num;
            ostringstream sgeek;
            sgeek << T->prob;
            num = sgeek.str();
            of << T->chStr + ":" + num + "\n";
            of.close();
        }
    }
    
};
class HuffmanLinkedList : public HuffmanBinaryTree{
    public:
    listNode *listHead;
    listNode *oldlistHead;

    HuffmanLinkedList(){
        listHead = new listNode("dummy", 0);
        listHead->next = NULL;
    }
    
    void constructor(string a, string b, string c, string d, string e){
        //HuffmanBinaryTree HBT = new HuffmanBinaryTree();
        getFileName(a, b, c, d, e);
        HBTree(listHead);
        consCharC();
        preOrder();
        inOrder();
        postOrder();
        
    }

    bool isEmpty(){
        if(listHead == NULL){
            return true;
        } else{
            return false;
        }
    }

    void findSpot(string a, int b){
        listNode* node = new listNode(a, b);
        listNode* spot = listHead;
        while(spot->next != NULL && spot->next->prob < b){
            spot = spot->next;
        }
        listInsert(spot, node);
    }

    void listInsert(listNode* S, listNode* N){
        listNode* curr = S->next;
        S->next = N;
        N->next = curr;
    }

    string printList(){
        listNode *pointer = listHead;

        string output = "listHead -> (";
        do{
            string num;
            ostringstream sgeek;
            sgeek << pointer->prob;
            num = sgeek.str();
            if(pointer -> next == NULL){
                output = output +  pointer ->chStr + "," + num + ", NULL) -> NULL";
            }else {
                output = output +  pointer -> chStr + "," + num + "," + pointer -> next-> chStr + ") -> (";
            }
            pointer = pointer -> next;
        }while(pointer != NULL);
        return output;
    }
};


int main(int argc, char** argv) {
        ifstream file;
        file.open(argv[1]);
        if(!file.is_open()) return -1;
        

        HuffmanLinkedList *HLL = new HuffmanLinkedList();
        string word;
        string ch;
        int probn = 0;
        while(file >> word){
                ch = word;
                file >> word;
                stringstream geek(word);
                geek >> probn;
                HLL -> findSpot(ch, probn);
                ofstream os(argv[6], ios::app);
                os << HLL -> printList();
                os << "\n";
                os.close();
        }
        
        
        HLL->constructor(argv[2], argv[3], argv[4], argv[5], argv[6]);

        file.close();
        
        return 0;
}
 
