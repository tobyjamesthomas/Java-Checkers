/**
 * Program Name: Checkers Board Game (Created with IntelliJ IDEA)
 * Coder: Toby Thomas
 * Date Started: 01-06-14
 * Date Finished: 01-23-13
 * Description: This is the board class. This is the heart of the game with all of its functions.
 */

package Checkers; //calls other classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Board extends JPanel implements ActionListener, MouseListener { //Board class beings, extends on JPanel class

    Data board; //declares new Data class to store the game's information
    boolean gameInProgress; //boolean to check if game is in progress
    int currentPlayer; //tracks whose turn it is
    int selectedRow, selectedCol; //tracks which squares have been selected
    movesMade[] legalMoves; //declares new movesMade array
    JLabel title; //title JLabel on frame
    JButton newGame; //newGame JButton on frame - starts a new game
    JButton howToPlay; //howToPlay JButton on frame - gives intro to Checkers and how to play
    JButton credits; //credits JButton on frame - displays credits
    JLabel message; //message JLabel on frame - indicates whose turn it is
    String Player1; //first player's name
    String Player2; //second player's name

    public Board() { //default constructor

        addMouseListener(this); //implements Mouse Listener

        //assigns all JLabels and JButtons to their values, as well as styles them
        title = new JLabel("Checkers!");
        title.setFont(new Font("Serif", Font.CENTER_BASELINE, 40));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.darkGray);
        howToPlay = new JButton("Checkers?");
        howToPlay.addActionListener(this);
        newGame = new JButton("New Game");
        newGame.addActionListener(this);
        credits = new JButton("Credits");
        credits.addActionListener(this);
        message = new JLabel("",JLabel.CENTER);
        message.setFont(new  Font("Serif", Font.BOLD, 14));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setForeground(Color.darkGray);
        
        board = new Data(); //assigns to new Data class
        getPlayersNames(); //calls to get players' names
        NewGame(); //calls to start a new game

    }

    public void actionPerformed(ActionEvent evt) { //implemented from Actions Listener

        Object src = evt.getSource();
        if (src == newGame) //if newGame button is pressed, a new game is created
            NewGame();
        else if (src == howToPlay) //if howToPlay button is pressed, instructions pop up
            instructions();
        else if (src == credits) //if credits button is pressed, credits pop up
            showCredits();
    }

    void NewGame() { //creates new game

        board.setUpBoard(); //sets up board
        currentPlayer = Data.player1; //indicates its player 1's move
        legalMoves = board.getLegalMoves(Data.player1); //searches for legal moves
        selectedRow = -1; //no square is selected
        message.setText("It's " + Player1 + "'s turn."); //indicates whose turn it is
        gameInProgress = true; //sets gameInProgress as true
        newGame.setEnabled(true); //enables newGame button
        howToPlay.setEnabled(true); //enables howToPlayButton
        credits.setEnabled(true); //enables credits button
        repaint(); //repaints board

    }

    public void getPlayersNames(){ //gets players names through JTextField

        JTextField player1Name = new JTextField("Player 1");
        JTextField player2Name = new JTextField("Player 2");

        //creates new JPanel to store the JTextFields
        JPanel getNames = new JPanel();
        getNames.setLayout(new BoxLayout(getNames, BoxLayout.PAGE_AXIS));
        getNames.add(player1Name);
        getNames.add(player2Name);

        //player inputs name through Confirm Dialog
        int result = JOptionPane.showConfirmDialog(null, getNames, "Enter Your Names!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        
        if (result == JOptionPane.OK_OPTION) { //if players give names, names are assigned
            Player1 = player1Name.getText();
            Player2 = player2Name.getText();
        } else { //otherwise default names are given
            Player1 = "Player 1";
            Player2 = "Player 2";
        }

    }

    void instructions() { //when howToPlay button is pressed, instruction Message Dialog appears

        //brief history of Checkers and a link to read the instructions - the link is not clickable
        String intro = "Checkers, called Draughts in most countries,\n" +
                "has been traced back to the 1300s, though it\n" +
                "may indeed stretch further into history than that.\n" +
                "These are the standard U.S. rules for Checkers.\n\n"+
                "Read how to play: http://abt.cm/1d0fHKE";

        JOptionPane.showMessageDialog(null, intro, "What is Checkers", JOptionPane.PLAIN_MESSAGE); //shows message

    }

    void showCredits() { //when credits button is pressed, credits Message Dialog appears

        String credits = "ICS3U | Ms. Shaw\n" + "By Toby Thomas\n" + "01/23/14"; //credits of game
        JOptionPane.showMessageDialog(null, credits, "Credits", JOptionPane.PLAIN_MESSAGE); //shows message

    }

    void gameOver(String str) { //when game is over

        message.setText(str); //indicates who won
        newGame.setEnabled(true); //enables newGame button
        howToPlay.setEnabled(true); //enables howToPlayButton
        credits.setEnabled(true); //enables credits button
        gameInProgress = false; //sets gameInProgress as false, until new game is initialized

    }

    public void mousePressed(MouseEvent evt) { //when the board is clicked

        if (!gameInProgress){ //if game is not in progress
            message.setText("Start a new game."); //indicates to start a new game
        }else { //otherwise, calculates which square was pressed
            int col = (evt.getX() - 2) / 40; //calculation of square's column
            int row = (evt.getY() - 2) / 40; //calculation of square's row
            if (col >= 0 && col < 8 && row >= 0 && row < 8) //if square is on the board
                ClickedSquare(row,col); //calls ClickedSquare
        }
    }

    void ClickedSquare(int row, int col) { //processes legal moves

        for (int i = 0; i < legalMoves.length; i++){ //runs through all legal moves
            if (legalMoves[i].fromRow == row && legalMoves[i].fromCol == col) { //if selected piece can be moved
                selectedRow = row; //assigns selected row
                selectedCol = col; //assigns selected column
                if (currentPlayer == Data.player1) //indicates whose turn it is
                    message.setText("It's " + Player1 + "'s turn.");
                else
                    message.setText("It's " + Player2 + "'s turn.");
                repaint(); //repaints board
                return;
            }
        }

        if (selectedRow < 0) { //if no square is selected
            message.setText("Select a piece to move."); //indicates player to pick a piece to move
            return;
        }

        for (int i = 0; i < legalMoves.length; i++){ //runs through all legal moves
            if (legalMoves[i].fromRow == selectedRow && legalMoves[i].fromCol == selectedCol //if already selected piece can move
                && legalMoves[i].toRow == row && legalMoves[i].toCol == col) { //and the selected piece's destination is legal
                MakeMove(legalMoves[i]); //make the move
                return;
            }
        }

        //when a piece is selected and player clicks elsewhere besides legal destination, program encourages player to move piece
        message.setText("Where do you want to move it?");  

    }

    void MakeMove(movesMade move) { //moves the piece

        board.makeMove(move); //calls makeMove method in Data class

        if (move.isJump()) { //checks if player must continue jumping
            legalMoves = board.getLegalJumpsFrom(currentPlayer, move.toRow, move.toCol);
            if (legalMoves != null) { //if player must jump again
                if (currentPlayer == Data.player1)
                    message.setText(Player1 + ", you must jump."); //indicates that player 1 must jump
                else
                    message.setText(Player2 + ", you must jump."); //indicates that player 2 must jump
                selectedRow = move.toRow; //assigns selected row to destination row
                selectedCol = move.toCol; //assigns selected column to destination column
                repaint(); //repaints board
                return;
            }
        }

        if (currentPlayer == Data.player1) { //if it was player 1's turn
            currentPlayer = Data.player2; //it's now player 2's
            legalMoves = board.getLegalMoves(currentPlayer); //gets legal moves for player 2
            if (legalMoves == null) //if there aren't any moves, player 1 wins
                gameOver(Player1 + " wins!");
            else if (legalMoves[0].isJump()) //if player 2 must jump, it indicates so
                message.setText(Player2 + ", you must jump.");
            else //otherwise, it indicates it's player 2's turn
                message.setText("It's " + Player2 + "'s turn.");
        } else { //otherwise, if it was player 2's turn
            currentPlayer = Data.player1; //it's now player 1's turn
            legalMoves = board.getLegalMoves(currentPlayer); //gets legal moves for player 1
            if (legalMoves == null) //if there aren't any moves, player 2 wins
                gameOver(Player2 + " wins!");
            else if (legalMoves[0].isJump()) //if player 1 must jump, it indicates so
                message.setText(Player1 + ", you must jump.");
            else //otherwise, it indicates it's player 1's turn
                message.setText("It's " + Player1 + "'s turn.");
        }

        selectedRow = -1; //no squares are not selected

        if (legalMoves != null) { //if there are legal moves
            boolean sameFromSquare = true; //declares boolean sameFromSquare
            for (int i = 1; i < legalMoves.length; i++) //runs through all legal moves
                if (legalMoves[i].fromRow != legalMoves[0].fromRow //if there are any legal moves besides the selected row
                        || legalMoves[i].fromCol != legalMoves[0].fromCol) { //and column
                    sameFromSquare = false; //declares sameFromSquare as false
                    break;
                }
            if (sameFromSquare) { //if true, the player's final piece is already selected
                selectedRow = legalMoves[0].fromRow;
                selectedCol = legalMoves[0].fromCol;
            }
        }

        repaint(); //repaints board

    }


    public void paintComponent(Graphics g) { //paints board

        //boarder around game board
        g.setColor(new Color(139,119,101));
        g.fillRect(0, 0, 324, 324);

        //creates checkered effect
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {

                //paints squares
                if ( row % 2 == col % 2 )
                    g.setColor(new Color(139,119,101));
                else
                    g.setColor(new Color(238,203,173));
                g.fillRect(2 + col*40, 2 + row*40, 40, 40);

                //paints squares with pieces on them
                switch (board.pieceAt(row,col)) {
                    case Data.player1:
                        g.setColor(Color.lightGray);
                        g.fillOval(4 + col*40, 4 + row*40, 36, 36);
                        break;
                    case Data.player2:
                        g.setColor(Color.darkGray);
                        g.fillOval(4 + col*40, 4 + row*40, 36, 36);
                        break;
                    case Data.playerKing1:
                        g.setColor(Color.lightGray);
                        g.fillOval(4 + col*40, 4 + row*40, 36, 36);
                        g.setColor(Color.white);
                        g.drawString("K", 27 + col*40, 36 + row*40);
                        break;
                    case Data.playerKing2:
                        g.setColor(Color.darkGray);
                        g.fillOval(4 + col*40, 4 + row*40, 36, 36);
                        g.setColor(Color.white);
                        g.drawString("K", 27 + col*40, 36 + row*40);
                        break;
                }
            }
        }

        if (gameInProgress) { //if game is in progress

            g.setColor(new Color(0, 255,0));
            for (int i = 0; i < legalMoves.length; i++) { //runs through all legal move
                //highlights, in green, all the possible squares the player can move
                g.drawRect(2 + legalMoves[i].fromCol*40, 2 + legalMoves[i].fromRow*40, 39, 39);
            }

            if (selectedRow >= 0){ //if a square is selected
                g.setColor(Color.white); //the square is highlighted in white
                g.drawRect(2 + selectedCol*40, 2 + selectedRow*40, 39, 39);
                g.drawRect(3 + selectedCol*40, 3 + selectedRow*40, 37, 37);
                g.setColor(Color.green);
                for (int i = 0; i < legalMoves.length; i++) { //its legal moves are then highlighted in green
                    if (legalMoves[i].fromCol == selectedCol && legalMoves[i].fromRow == selectedRow)
                        g.drawRect(2 + legalMoves[i].toCol*40, 2 + legalMoves[i].toRow*40, 39, 39);
                }
            }
        }
    }

    //implements Mouse entered, clicked, released and exited
    public void mouseEntered(MouseEvent evt) { }
    public void mouseClicked(MouseEvent evt) { }
    public void mouseReleased(MouseEvent evt) { }
    public void mouseExited(MouseEvent evt) { }

}




    