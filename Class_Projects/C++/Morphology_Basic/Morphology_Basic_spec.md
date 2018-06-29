Morphology Basic project:

Input File:
	1. Input1 (argv[1]): a txt file representing a binary image with header.
    2. Input2 (argv[2]): a txt file representing a binary image of a structuring element 
   with header and the origin of the structuring element. The format of the structuring element is as follows: 
   1th text line is the header; the 2nd text line is the position (w.r.t. index) of the origin of the structuring element
   then follows by the rows and column of the structuring element.

Output Files:
	1. Output1 (argv[3]): the result of dilation image with header, should be the same dimension as input1
    2. Output2 (args[4]): the result of erosion image with header, should be the same dimension as input1
    3. Output3 (args[5]): the result of closing image with header, should be the same dimension as input1
    4. Output4 (args[6]): the result of opening image with header, should be the same dimension as input1


** Use argv[] for both input and output files.

*******************************************************
Data structure:
	- numRowsImg (int)
	- numColsImg (int)
	- minImg (int)
	- maxImg (int)

  	- numRowsStructElem (int)
	- numColsStructElem (int)
	- minStrctElem (int)
	- maxStrctElem (int)

	- rowOrigin (int)
	- colOrigin (int)

	- rowFrameSize (int)
	- colFrameSize (int)

	- imgAry (int **) // a 2D array, to store the input image, 
			// needs to dynamically allocate at run time
			// of size numRowsImg + rowFrameSize by numColsImg + colFrameSize.
	
	- morphAry (int **) // a 2D array, need to dynamically allocate at run time
			// of size numRowsImg + rowFrameSize by numColsImg + colFrameSize.
	
   	 - structElemAry (int **) //a 2D array, need to dynamically allocate at run time
			// of size numRowsStructElem by numColsStructElem.

   	- methods:
	- computeFrameSize(...) 
        // for this project, rowFrameSize set to numRowsStructElem (half to the top and half to the bottom) 
	    // colFrameSize set to numColsStructElem (half to the left and half to the right) 
    
	- loadImage  // load imgAry from input1
	- loadstruct  // load structElem from input2
    - zeroFrameImg // frame the input image with zero

	- initMorphAry( ) // initialize morphAry to zero 

	- delation (i,j) // place the origin of the structuring element at pixel(i,j), 
                     // i begins at (numRowsStructElem / 2), and j begins at (numColsStructElem / 2)
	- erosion (i,j) // as above
	- closing (i,j) // as above
	- opening (i,j) // as above
	- prettyPrint () //pretty print the given array to the console.
	- outputResult ()   
    
*******************************************************
Algorithm Steps:

 Step 0:
	- Open input file
    - get (numRowsImg, numColsImg, minImg, maxImg) from input1
    - get (numRowsStrctElem, numColsStrctElem, minStrctElem, maxStrctElem) from input2
    - get (rowOrigin, colOrigin) from input2

 Step 1:
	- computeFrameSize
    
 Step 2:
	- dynamically allocate structElemAry
    - loadImage() // load image into imgAry
    - zeroFrameImg()
    - PrettyPrint(imgAry) // pretty print the imgAry to the console.
    - dynamically allocate morphAry with extra rows and extra cols.

 Step 3:
    - dynamically allocate structElemAry
    - loadstruct // load input2 to stuctElemAry
    - prettyPrint(structElemAry) //pretty print the array to the console.
    
 Step 4:
    - initMorphAry() // initialize morphAry to zero
    - call dilation
    - prettyPrint(morphAry)
    - outputResult // write the result to output1
    
 Step 5:
    - initMorphAry() // initialize morphAry to zero
    - call erosion
    - prettyPrint(morphAry)
    - outputResult // write the result to output1
    
 Step 6:
    - initMorphAry() // initialize morphAry to zero
    - call closing
    - prettyPrint(morphAry)
    - outputResult // write the result to output1
    
 Step 7:
    - initMorphAry() // initialize morphAry to zero
    - call opening
    - prettyPrint(morphAry)
    - outputResult // write the result to output1
    
 Step 8:
    - close all files.
    