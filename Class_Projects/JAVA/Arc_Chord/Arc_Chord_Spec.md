Morphology Basic project:

Input File:
	1. Input1 (argv[]) : A text file contains the boundary points of an object
    2.Input2 (console): ask the user from condole for K. 2*K will be used as
	the length of the arc-Chord in the maximum arc-Chord distance computation.

Output Files:
	1. Output1 (argv[]): The result of the maximum arc-Chord distance of the object boundary points plus corner indicating label. 
    2. Output2 (argv[]): Pretty print (displaying) as an image of the result of the Maximum arc-Chord distance corner detection, where corner points are printed as 8 and non-corner points are printed as 1.
    3. Output3 (argv[]): for all debugging output


** Use argv[] for both input and output files.

*******************************************************
Data structure:

  An image class
    - numRows (int) 
	- numCols (int)
	- minVal (int) 
	- maxVal (int) 	
	- img (int**) // a 2D array for display, initially set to 0
	- constructor
    - plotPt2Img() // put each point (x, y)’s corner indicating value (1 or 9) at Img(x, y)
	- prettyPrint (img) // print img, if pixel(i,j) == 0
                        // print blank, otherwise, print its value.

 A boundaryPt class
 	- x (int)
	- y (int)
	- maxVotes (int) // initallized to 0
	- maxDist (double) // to keep track of the maximum distance, 
                       // initialized to 0.0
	- corner (int) // initallized to 1, not corner
	- constructor
			
 A arcChord class	
  	- chordLength (int) // Ask user to input K from console,  
                        // chordLength is set to (2*K)
    - countPts (inFile) // reads and returns the count of the boundary points 
		                // in the input file.
	- numPts (int) // get from input-1
    - PtAry (boundaryPt *) // an 1D array of boundaryPt class, need to dynamically allocate.
		                   // use mod function to get index during compute the 2K points without extending the tail of the array, the array size is numPts.
    - printPtAry() // print the content of the entire PtAry
	- chordAry (double *) // used during computation, size of chordLength, need to be dynamically allocated
	- P1 (int) // the array index of the first-end of the arc Chord; initially set to 0
	- P2 (int) // the array index of the second-end of the arc Chord; initially set to chordLength
	- loadData (inFile) // read and store data to PtAry 
 	- computeDistance (P1, P2, Pt) // It computes the orthogonal distance 
				// from Pt to the line formed by P1 and P2 (with respect
				// to their xy coordinates.
				// it returns the computed distance.
				// Use the distance formula given in class
	- findMax (chordAry) // find which index in chordAry having the maximum distance and returns the index that has the maximum distances, for voting. 

	- computeLocalMaxima (PtAry) // Go thru the entire PtAry, p(i) is a local maxima 
                                 // iff p(i)’s maxVote >= all the maxVotes of 
		                         // its 1x5 neighborhood: two points from its left 
                                 // and two points from its right. 
    - setCorner (PtAry)  // a boundary point, p(i) is a corner (returns 9), if 
                         // (a) p(i) is a local maxima, and 
                         // (b) within its 5 neighborhood, p(i-2), p(i-1), p(i+1), p(i+2)
		                 // only p(i-1) or p(i+1) can be a local maxima, 
                         // otherwise, p(i) is not a corner (return 1).

*******************************************************
Algorithm Steps:

 Step 0:
	- inFile <- Open input file
    - get (numRows, numCols, minVal, maxVal, label) from input file
    - dynamically allocate image array of size numRows by numCols
    - numPts <- countPts (inFile)
    - inFile <- open the input file the second time.
    - dynamically allocate PtAry with size of numPts
    - K <- get from the user from console
    - chordLength <- (2*K)
    - dynamically allocate chordAry with size of chordLength // initiallied to 0.0
	- loadData (inFile)

 Step 1:
	- P1 <- 0
    - P2 <- chordLength - 1
    
 Step 2:
	- index <- 0
    - currPt <- P1 + 1

 Step 3:
    - dist <-- computeDistance (P1, P2, currPt )
    - chordAry[index]<- dist
	- index ++
    - currPt ++

    
 Step 4:
    - repeat step 3 while index  < chordLength
    
 Step 5:
    - print chordAry to debugging file (Output3)
    
 Step 6:
    - maxIndex <-- findMaxDist(chordAry) 
            // find the max of distances of all points in chordAry 
			// and returns that index
    - whichIndex <-- P1 + maxIndex  	   
    - PtAry[whichIndex]'s maxVotes ++
    - update PtAry[whichIndex]'s maxDist if it is less then chordAry[maxIndex]

    
 Step 7:
    - print PtAry from P1 to P2 to output3, debugging file
    
 Step 8:
    - Increment P1, and P2, and then mod (P1, numPts) and mod (P2, numPts)
		      // so the computation will continue wrapped around the boundray 

 Step 9:
    - repeat step 2 to step 8 until P2 == (chordLength / 2)
    
 Step 10:
    - printPtAry() // five pts per text line
    
 Step 11:
    - computeLocalMaxima(PtAry)

 Step 12:
    - SetCorner (PtAry) do for all points in boundPtAry[index], index from 0 to numPts-1

 Step 13:
    - output only (x, y, corner) of the entire PtAry to output1

 Step 14:
    - Img <-- create an image of size numRows by numCols

 Step 15:
    - plotPt2Img() // put each point (x, y)'s corner value (1 or 8) at Img(x, y)

 Step 16:
    - PrettyPrint(img) to output2

 Step 17;
    - close all files.
    