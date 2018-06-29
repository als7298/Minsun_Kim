Heap Sort Project:

Input File:
	It contains a list of numbers (positive integers).

Output Files:
	1. It should contains the debugging output
	2. It should contains the sorted input numbers.

** Use argv[] for both input and output files.

*******************************************************
Data structure:
   HeapSort class
	- int rootIndex, fatherIndex, leftKidIndex, rightKidIndex, minKidIndex

   Methods:
	- constructor
	- buildHeap
	- deleteHeap
	- insertOneDataItem
		//insert data at the end of the Array.
	- getRoot
	- deleteRoot
		//move the last item in Array to root.
	- bubbleUp
	- bubbleDown
	- isLeaf
		//use boolean, return true if both kid's index are out of range.
		//(return true, if both given kids are leaf.)
		//Else, return false.
	- isRoot
		//return true, if given kid's index is 1. It means, if given kid's index is root.
	- findMinKidIndex
	- isHeapEmpty
	- isHeapFull
	- printHeap

   *On Main
	- int heapAry[]
********************************************************
Algorithm Steps(HeapSort):
 Step 0:
	- Open input file
	- Count the number of data in input file
	- Initialize the size of heapAry[] with count+1

 Step 1:
	- Call buildHeap()
    
 Step 2:
	- Call deleteRoot()
    
**********************************************************
Algorithm Steps(buildHeap):
 step 1: 
    - inFile <-- open the input file for the *second time*
 	    rootInex <-- 1

 step 2: 
    - data <-- get a data item from the inFile

 step 3: 
    - insertOneDataItem (data) // put data at the end of heap	
    - kidIndex <-- heapAry[0] // last index
    - bubbleUp (kidIndex) // written in recursion see algorithm below
   
 step 4: 
    - printHeap to outFile1 // debugging file in the real life, this is only for your debugging purpose

 step 5: 
    - repeat step 2 - step 4 while inFile is NOT empty
**********************************************************
Algorithm Steps(deleteRoot):
 step 1:
    - data <-- getRoot (..)
    - print data to outFile2 // the sorted output
    - deletRoot (..) // move the last item in headAry to root
    - fatherIndex <-- rootIndex
    - bubbleDown (fatherIndex) // written in recursion see algorithm below

 step 2: 
    - printHeap to outFile1 // debugging file
            
 step 3: 
    - repeat step 1 - step 2 while the heap is NOT empty
**********************************************************
bubble Up (kidIndex):
if( isRoot(kidIndex)) 
	return
else 
	fatherIndex <-- kidIndex / 2
	if heapAry[kidIndex] < heapAry[fatherIndex]
	   swap the two
        bubbleUp(fatherIndex)
        
**********************************************************
bubble Down (fatherIndex):
if(isLeaf(fatherIndex)) 
	return
else 
	leftKidIndex <-- fatherIndex * 2
	rightKidIndex <-- fatherIndex * 2 + 1

	minIndex <-- findMinKidIndex(leftKidIndex, rightKidIndex)
			// do NOT check leftKidInde or rightKidIndex that is out of range!!!

	if heapAry[minIndex] < heapAry[fatherIndex]
	   swap the two
       
	 bubbleDown(minIndex) 