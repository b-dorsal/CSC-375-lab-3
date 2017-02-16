Assignment 3 - Peg Solitaire - README

Brian Dorsey 2016
bdor528@gmail.com

How To Run:
  java termMain "# remaining pegs" "work depth" <opts>

 -D displays the list of steps, at each depth, taken to get the result.

 -X displays a graphical representation of the final resulting board in a window

  examples:
    java PegSolSolver 2 10 -D -X
    java PegSolSolver 25 2 -D 
    java PegSolSolver 7 4 -X

Source Files Includes With This Project:
  PegSolSolver.java       Board.java      	BoardEvaluate.java
  Move.java               Renderer.java     

Procedure
	The process starts with a single game board that represents the start of the game. The evaluator examines all the possible moves on this board and their children until the search depth set with the first argument is reached. This is repeated until a board with a peg count equal to the requested peg count is found. The arguments -D and -X are display options. -D shows to list of moves taken to get to the result acquired. -X displays the graphical representation of the resulting game board. Sometimes, the selected depth does not allow the evaluator to find a results. In this case, the program will fail and notify the user that they should retry the operation with a higher depth count. This will take longer but will find a result at some depth, if one exists.