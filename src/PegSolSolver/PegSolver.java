package PegSolSolver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

/**
 * CSC-375 Asn 3
 * PegSolver.java - Peg Solitaire board solver
 * Purpose: PegSolver class contains main method to collect user arguments and solve board using them as constraints.
 *
 * @author Brian Dorsey
 * @version 1.0 12/15/2016
 */

public class PegSolver {

    static private ForkJoinPool forkJoinPool = new ForkJoinPool();

    /**
     * The main method for the Peg Solitaire Solver.
     *
     * @param args max remaining pegs, max search depth, -D display results, -X display visual
     */

    public static void main(String args[]) {
        //Set the parameters for the program.
        int MAXREMAINING = Integer.parseInt(args[0]);
        int DEPTH = Integer.parseInt(args[1]);

        System.out.print("Running...");
        //Solve this board with the argument values provided.
        Board game = new Board();
        ArrayList<Board> results = solveBoard(game, MAXREMAINING, DEPTH); // Solve the board
        if (results == null) { //This happens if the program cant find a board with the selected max pegs
            System.out.println("Failed. Retry with larger depth.");
        } else {
            System.out.println("done.");
            //Set up the board renderer, and a frame to display it in

            Renderer render = new Renderer();

            JFrame f = new JFrame();
            f.setResizable(false); //No resizing.

            f.setSize(500, 500);
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setTitle("Peg Sol");//title.
            f.add(render);

            //Interpret args
            for (String s : args) {
                if (s.equals("-D")) {//Display path to result
                    for (Board b : results) {
                        System.out.println(b.toString());
                    }
                } else if (s.equals("-X")) { //Display result in graphical window
                    f.setVisible(true);//show the JFrame
                    for (Board b : results) {
                        if (b.getRemainingPegs() == MAXREMAINING) {
                            render.setGame(b);
                            render.refresh();
                        }
                    }
                }
            }
        }
    }

    /**
     * Solves the given board with the given constraints for max pegs and search depth.
     * For each turn, this method calculated the best move to make next and loops until finished.
     * @param start Board object for a start location.
     * @param pegs The max remaining pegs requested on the board. Winning number.
     * @param searchDepth the amount of recursions for evaluation.
     * @return ArrayList containing the move path to the winning board.
     */
    private static ArrayList<Board> solveBoard(Board start, int pegs, int searchDepth) {

        ArrayList<Board> results = new ArrayList<>(); //list for the results
        results.add(start);//add the initial board.

        int last = -1; // the last score, to double check that the two boards arent the same
        Board next = new Board();
        next.copy(start, next);
        while (next.getRemainingPegs() != pegs) {
            int win = Integer.MIN_VALUE;
            last = next.getRemainingPegs();

            //Gather all the possible moves.
            for (Move m : next.getRemainingMoves()) {
                Board temp = new Board();
                next.copy(next, temp);
                temp.jump(m.x, m.y, m.dir);

                //Create a new evaluation task and execute it.
                BoardEvaluate evaltask = new BoardEvaluate(start, pegs, searchDepth, 0);
                int result = forkJoinPool.invoke(evaltask);
                if (result > win) {
                    win = result;
                    next.copy(temp, next);
                }
            }
            //If both this board and the last seen board peg counts are the same we need to exit the program.
            //An infinite loop will happen if not.
            if (next.getRemainingPegs() == last) {
                return null;
            }
            Board test = new Board();
            test.copy(next, test);
            results.add(test);
        }
        return results;
    }
}
//Brian Dorsey 2016