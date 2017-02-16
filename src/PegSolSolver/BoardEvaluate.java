package PegSolSolver;

import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

/**
 * CSC-375 Asn 3
 * BoardEvaluate.java - Peg Solitaire board solver
 * Purpose: Recursive task expands the current board's possible moves till the depth specified, until returning a final result.
 *
 * @author Brian Dorsey
 * @version 1.0 12/15/2016
 */

public class BoardEvaluate extends RecursiveTask<Integer> {

    private Board load; //Board object to examine
    private final int maxDepth; //Maximum depth to work
    private int curDepth;   //current depth of this recurse
    private final int MAXREMAINING; //max remaining pegs expected

    /**
     * Board evaluation constructor
     * @param workLoad Board object to evaluate.
     * @param MAXREMAINING The max remaining pegs requested on the board. Winning number.
     * @param maxDepth the amount of recursions for evaluation.
     * @param curDepth the current depth level.
     */
    public BoardEvaluate(Board workLoad, int MAXREMAINING, int maxDepth, int curDepth) {
        this.load = new Board();
        workLoad.copy(workLoad, load);
        this.maxDepth = maxDepth;
        this.curDepth = curDepth;
        this.MAXREMAINING = MAXREMAINING;
    }

    /**
     * Computes the current work.
     * @return returns the integer score sum of the search done.
     */
    protected Integer compute() {
        //System.out.println("depth: " + curDepth);
        int sum = 0;
        if (curDepth == maxDepth) {
            if (load.getRemainingPegs() == MAXREMAINING) {
                sum = 1;
            }
            curDepth++;
            return sum;
        } else {

            if (load.getRemainingPegs() == MAXREMAINING) {
                sum = 1;
            } else if (load.getRemainingMoves().size() > 0) {

                curDepth++;

                ArrayList<BoardEvaluate> subtasks = createSubtasks();
                for (BoardEvaluate subtask : subtasks) {
                    subtask.fork();
                }

                for (BoardEvaluate subtask : subtasks) {
                    sum += subtask.join();
                }
            }
            return sum;
        }
    }

    /**
     * Returns all the possible remaining moves of this cur
     * @return  ArrayList of all possible moves left on the board.
     */
    private ArrayList<BoardEvaluate> createSubtasks() {
        ArrayList<BoardEvaluate> subtasks = new ArrayList<>();

        for (Move m : load.getRemainingMoves()) {
            Board temp = new Board();
            this.load.copy(load, temp);
            temp.jump(m.x, m.y, m.dir);

            subtasks.add(new BoardEvaluate(temp, this.MAXREMAINING, maxDepth, curDepth));
        }
        return subtasks;
    }

}
//Brian Dorsey 2016