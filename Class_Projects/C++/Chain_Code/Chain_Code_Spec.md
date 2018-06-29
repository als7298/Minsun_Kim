Chain Code project:

Input File:
	1. Input (use argv[]): a binary image contain only one object without hole.

Output Files:
	1. outFile1: it is for storing the chain-code of the object for future image decompression.  	 
	   Output format:		
		  #rows #cols min max // image header use one text line
		  Label startRow startCol code1 code2 code3 .... // one text line with one blank space between codes.
    2. outFile2: it is for debugging and for printing the chain code in a more readable format as follows:
        Output format:
		  #rows #cols min max // one text line
		  startRow startCol Label // one text line
		  code1 code2 code3 .... // 15 chain codes per text line with one blank space in between codes.

** Use argv[] for both input and output files.

*******************************************************
Data structure:

 An image class  
 	- numRows (int)
	- numCols (int)
	- minVal (int)
	- maxVal (int)	
	- imgAry (int **) a 2D array to store the input image, 
		needs to dynamically allocate at run time (numRows+2 by numCols+2)

	methods:
	- constructor(s)
	- loadImage () // Read from the input label file onto imageAry begin at (1,1)
	- zeroFramed ()

 A Point class
    - row (int)
	- col (int)

 A chainCode class		 
	- neighborCoord[8] (Point) // Given a point, p(i,j), this array provide the x-y coordinate of p(i,j)'s eight neighbors 
                               // w.r.t the chain code directions. 
		                       // i.e., p(i,j)'s neighbor of chain-code direction of 2,
		                       // the neighbor's x-y coordinate would be (i-1, j)  
	- startP (point) // the x-y coordinate of the first pixel of the object.
	- currentP (Point) // current none zero border pixel
	- nextP (Point)    // next none zero border neighbor pixel
	- lastQ (int) // Range from 0 to 7, it is the direction of the last zero scanned from currentP
	- nextQ (int)
	- nextDirTable[8] // You may *hard code* this table as given in class
		              // the index is from currentP looking at the direction to the last zero
		              // nextDirTable[index] is from nextP looking at the last zero.		
	- nextDir (int) // the next scanning direction of currentP 's neighbors to find nextP, range from 0 to 7, need to mod 8.
	- PchainDir // chain code direction from currentP to nextP
	
	methods: 
	- constructors
	- getChainCode()
	- loadNeighborsCoord(...)
	- findNextP (...)
	- getChainDir(currentP, nextQ) // on  your own getChianDir will scan currentP 's 8 neighbors in 
                                   //neighborCoord [] array from nextQ direction (mod 8)
		                           //until a none zero neighbor is found, chainDir is the index of neighborCoord[] of the none zero neighbor
	- prettyPrint


*******************************************************
Algorithm Steps (Main) :

 Step 0:
	- inFile <- Open input file
    - open output files.
    - read image header from inFile
    - output the image header to output files.

 Step 1:
	- imgAry <- dynamically allocated
    
 Step 2:
	- loadImage()

 Step 3:
    - zeroFramed()

 Step 4:
    - getChainCode()
    
 Step 5:
    - close all files
    
*******************************************************
Algorithm Steps (getChainCode) :

 Step 0:
	- scan imgAry from Left to Right & Top to Bottom

 Step 1:
	- if (imgAry(i, j) > 0) then output i, j, imgAry(i,j) to both output files.
    - startP <- (i, j)
    - currentP <- (i, j)
    - lastQ <- 4
    
 Step 2:
	- nextQ <- mod(lastQ+1, 8)

 Step 3:
    - PchainDir <- findNextP (currentP, nextQ, nextP) // nextP will be determined inside the findNextP method

 Step 4:
    - output PchainDir to both output files
    
 Step 5:
    - lastQ <-- nextDirTable[PchainDir]	
    - currentP <-- nextP // nextP was determined inside the findNextP method. 

 Step 6:
    - repeat step 2 to 5 until currentP == startP

*******************************************************
Algorithm Steps (findNextP) :

 step 0: 
    - loadNeighborsCoord (currentP) 
	
 step 1: 
    - chainDir <-- getChainDir(currentP, nextQ)

 step 2: 
    - nextP <-- neighborCoord [chainDir]

 step 3: 
    - returns chainDir 	

