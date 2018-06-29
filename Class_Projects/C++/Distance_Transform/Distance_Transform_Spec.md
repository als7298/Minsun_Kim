Distance Transform project:

Input File:
	1. Input1 (argv[]): a binary image

Output Files:
	1. Output1 (argv[]): Create a distance transform image from the result of Pass-2 with updated image header (numRows       numCols newRowVal newColVal) for future processing.
    2. Output2 (args[]): Pretty print the result of the Pass-1 and Pass-2.

** Use argv[] for both input and output files.

*******************************************************
Data structure:
	- numRows (int)
	- numCols (int)
	- minVal (int)
	- maxVal (int)
	- newMinVal (int)
	- newMaxVal (int)
	- ZeroFramedAry (int **) //a 2D array, need to dynamically allocate at run time of size numRows + 2 by numCols + 2.
	- NeighborAry[5](int) 
	- methods:
	- constructor(s)
		  // need to dynamically allocate ZeroFrameAry 
		  // assign values to numRows,..., etc.
 	- zeroFramed  
	- loadImage 
          // Read from the input file onto ZeroFrameAry  
          // the first pixel of input image is loaded 
          // at ZeroFrameAry[1][1]
	- loadNeighbors // load the respective neighbors of given pixel(i,j)
    - fistPassDistance // Using the algorithm steps given in class. 
	- secondPassDistance // Using the algorithm steps given in class. 
	- prettyPrint // if p(i,j) == 0 print use 2 blank space else print p(i,j) use 2 digit space 
     	
*******************************************************
Algorithm Steps:

 Step 0:
	- Open input file
    - read the image header and dynamically allocate zeroFramedAry with extra 2 rows and 2 cols.

 Step 1:
    - zeroFrame the zeroFramedAry
    
 Step 2:
    - loadImage()

 Step 3:
    - firstpassDistance // 8-distance algorithm
    
 Step 4:
    - prettyPrint the result of pass-1 to output file 2

 Step 5:
    - secondpassDistance // 8-distance algorithm
    
 Step 6:
    - prettyPrint the result of pass-2 to output file 2

 Step 7:
    - print the result of pass-2 to output file 1 without the 2extra rows and cols.
    - update the image header
    
 Step 8:
    - close all files.
    