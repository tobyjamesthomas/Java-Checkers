/**
 * Program Name: Checkers Board Game (Created with IntelliJ IDEA)
 * Coder: Toby Thomas
 * Date Started: 01-06-14
 * Date Finished: 01-23-13
 * Description: This is the movesMade class. This is organizes the selected pieces from and to row and column.
 */

package Checkers; //calls other classes

class movesMade { //movesMade class begins

    int fromRow, fromCol; //declares from row and column as public ints
    int toRow, toCol; //declares to row and column as public ints

    movesMade(int r1, int c1, int r2, int c2) { //movesMade constructor takes in selected squares and assigns them to public ints

        fromRow = r1;
        fromCol = c1;
        toRow = r2;
        toCol = c2;

    }

    boolean isJump() { //checks if move is a jump

        return (fromRow - toRow == 2 || fromRow - toRow == -2);

    }

}