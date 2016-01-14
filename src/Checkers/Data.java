/**
 * Program Name: Checkers Board Game (Created with IntelliJ IDEA)
 * Coder: Toby Thomas
 * Date Started: 01-06-14
 * Date Finished: 01-23-13
 * Description: This is the Data class. This is brain of the game, calculating all legal moves.
 */

package Checkers; //calls other classes
import java.util.ArrayList;

class Data { //Data class begins

    public static final int //declares final ints, representing the state of a square
    blank = 0,
    player1 = 1,
    playerKing1 = 2,
    player2 = 3,
    playerKing2 = 4;

    private int[][] board; //declares an int array called board

    public Data() { //Data default constructor

        board = new int[8][8]; //creates an 8 by 8 board
        setUpBoard(); //calls setUpBoard

    }

    public void setUpBoard() { //sets up board

        for (int row = 0; row < 8; row++) {

            for (int col = 0; col < 8; col++) {

                if ( row % 2 == col % 2 ) { //for all dark squares

                    if (row < 3) //if in top 3 rows
                        board[row][col] = player2; //squares are assigned to player 2
                    else if (row > 4) //if in bottom 3 rows
                        board[row][col] = player1; //squares are assigned to player 1
                    else //otherwise, middle rows are empty
                        board[row][col] = blank;

                } else //all light squares are empty
                    board[row][col] = blank;

            }

        }

    }

    public int pieceAt(int row, int col) { //method that returns pieces' location

        return board[row][col];

    }

    public void makeMove(movesMade move) { //method that takes in movesMade type and makes a move

        makeMove(move.fromRow, move.fromCol, move.toRow, move.toCol);

    }

    public void makeMove(int fromRow, int fromCol, int toRow, int toCol) { //makesMove (in database sense)

        board[toRow][toCol] = board[fromRow][fromCol]; //piece that was in original square is now in new square
        board[fromRow][fromCol] = blank; //the original square is now blank

        if (fromRow - toRow == 2 || fromRow - toRow == -2){ //if a move is a jump

            //the player jumps
            int jumpRow = (fromRow + toRow) / 2;
            int jumpCol = (fromCol + toCol) / 2;
            board[jumpRow][jumpCol] = blank; //the original square is not blank

        }

        if (toRow == 0 && board[toRow][toCol] == player1){ //if a player 1 piece reaches top row
            board[toRow][toCol] = playerKing1;
        }

        if (toRow == 7 && board[toRow][toCol] == player2){ //if a player 2 piece reaches bottom row
            board[toRow][toCol] = playerKing2; //it becomes a king
        }
    }

    public movesMade[] getLegalMoves(int player) { //determines legal moves for player

        if (player != player1 && player != player2) //if method is not called with a player
            return null; //null is returned

        int playerKing;

        //identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        } else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves

        for (int row = 0; row < 8; row++){ //looks through all the squares of the boards

            for (int col = 0; col < 8; col++){

                if (board[row][col] == player || board[row][col] == playerKing){ //if a square belongs to the player

                    //check all possible jumps around the piece - if one found the player must jump
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                        moves.add(new movesMade(row, col, row+2, col+2));
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                        moves.add(new movesMade(row, col, row-2, col+2));
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                        moves.add(new movesMade(row, col, row+2, col-2));
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                        moves.add(new movesMade(row, col, row-2, col-2));

                }

            }

        }

        if (moves.size() == 0){ //if there are no jumps

            for (int row = 0; row < 8; row++){ //look through all the squares again

                for (int col = 0; col < 8; col++){

                    if (board[row][col] == player || board[row][col] == playerKing){ //if a square belongs to the player

                        //check all possible normal moves around the piece - if one found, add it to the list
                        if (canMove(player,row,col,row+1,col+1))
                            moves.add(new movesMade(row,col,row+1,col+1));
                        if (canMove(player,row,col,row-1,col+1))
                            moves.add(new movesMade(row,col,row-1,col+1));
                        if (canMove(player,row,col,row+1,col-1))
                            moves.add(new movesMade(row,col,row+1,col-1));
                        if (canMove(player,row,col,row-1,col-1))
                            moves.add(new movesMade(row,col,row-1,col-1));

                    }

                }

            }

        }

        if (moves.size() == 0){ //if there are no normal moves
            return null; //the player cannot move
        }else { //otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }

    }

    public movesMade[] getLegalJumpsFrom(int player, int row, int col){ //determines legal jumps for player

        if (player != player1 && player != player2) //if method is not called with a player
            return null; //null is returned

        int playerKing;

        //identifies player's kings
        if (player == player1){
            playerKing = playerKing1;
        }else {
            playerKing = playerKing2;
        }

        ArrayList moves = new ArrayList(); //creates a new Array to story legal moves

        if (board[row][col] == player || board[row][col] == playerKing){

            //if there is a possible jump, add it to list
            if (canJump(player, row, col, row+1, col+1, row+2, col+2))
                moves.add(new movesMade(row, col, row+2, col+2));
            if (canJump(player, row, col, row-1, col+1, row-2, col+2))
                moves.add(new movesMade(row, col, row-2, col+2));
            if (canJump(player, row, col, row+1, col-1, row+2, col-2))
                moves.add(new movesMade(row, col, row+2, col-2));
            if (canJump(player, row, col, row-1, col-1, row-2, col-2))
                moves.add(new movesMade(row, col, row-2, col-2));

        }

        if (moves.size() == 0){ //if there are no possible moves
            return null; //null is returned
        }else { //otherwise, an array is created to store all the possible moves
            movesMade[] moveArray = new movesMade[moves.size()];
            for (int i = 0; i < moves.size(); i++){
                moveArray[i] = (movesMade)moves.get(i);
            }
            return moveArray;
        }
    }

    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3){ //method checks for possible jumps

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) //if destination row or column is off board
            return false; //there is no jump, as the destination doesn't exist

        if (board[r3][c3] != blank) //if the destination isn't blank
            return false; //there is no jump, as the destination is taken

        if (player == player1) { //in the case of player 1
            if (board[r1][c1] == player1 && r3 > r1) //if destination row is greater than the original
                return false; //there is no jump, as player 1 can only move upwards
            if (board[r2][c2] != player2 && board[r2][c2] != playerKing2) //if the middle piece isn't player 2's
                return false; //there is no jump, as player 1 can't jump his own pieces
            return true; //otherwise, jump is legal
        }else { //in the case of player 2
            if (board[r1][c1] == player2 && r3 < r1) //if destination row is less than the original
                return false; //there is no jump, as player 2 can only move downwards
            if (board[r2][c2] != player1 && board[r2][c2] != playerKing1) //if the middle piece isn't player 1's
                return false; //there is no jump, as player 2 can't jump his own pieces
            return true; //otherwise, jump is legal
        }

    }

    private boolean canMove(int player, int r1, int c1, int r2, int c2){ //method checks for possible normal moves

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) //if destination row or column is off board
            return false; //there is no move, as the destination doesn't exist

        if (board[r2][c2] != blank) //if the destination isn't blank
            return false; //there is no move, as the destination is taken

        if (player == player1) { //in the case of player 1
            if (board[r1][c1] == player1 && r2 > r1) //if destination row is greater than the original
                return false; //there is no move, as player 1 can only move upwards
            return true; //otherwise, move is legal
        }else { //in the case of player 2
            if (board[r1][c1] == player2 && r2 < r1) //if destination row is less than the original
                return false; //there is no move, as player 2 can only move downwards
            return true; //otherwise, move is legal
        }

    }

}