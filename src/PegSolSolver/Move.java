package PegSolSolver;
/**
 * CSC-375 Asn 3
 * Move.java - Peg Solitaire board solver
 * Purpose: Handle basic move information.
 *
 * @author Brian Dorsey
 * @version 1.0 12/15/2016
 */

public class Move {
    public final int x, y, dir;

    /** *
     * Sets the game object to display.
     * @param x X position.
     * @param y Y position.
     * @param dir Int direction to move to.
     */
    public Move(int x, int y, int dir){
        this.x=x;
        this.y=y;
        this.dir=dir;
    }
}

