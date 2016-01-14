/**
 * Program Name: Checkers Board Game (Created with IntelliJ IDEA)
 * Coder: Toby Thomas
 * Date Started: 01-06-14
 * Date Finished: 01-23-13
 * Description: This is our summative final project, requiring us to use the skills learned in the course to create our
 * own program. I have decided to create a Checkers board game. This program includes four classes, a main, Board class,
 * CheckersMove class and Data class.
 */

package Checkers; //calls other classes
import java.awt.*;
import javax.swing.*;

public class Checkers extends JFrame { //Checkers class begins, extends on JFrame class

    public static void main (String [] args) { //main method to start the board game

        JFrame game = new JFrame(); //creates new frame

        //set the frame's main settings
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.getContentPane();
        game.pack();
        game.setSize(324,454);
        game.setResizable(false); //the window is not resizable
        game.setLayout(null);
        game.setVisible(true);
        game.setBackground(new Color(225, 225, 225));

        //creates new Board and adds its components
        Board board = new Board();
        game.add(board);
        game.add(board.title);
        game.add(board.newGame);
        game.add(board.howToPlay);
        game.add(board.credits);
        game.add(board.message);

        //places components on the frame in the correct places
        board.setBounds(0,80,324,324);
        board.title.setBounds(0,0,324,50);
        board.newGame.setBounds(6, 50, 100, 30);
        board.howToPlay.setBounds(112, 50, 100, 30);
        board.credits.setBounds(218, 50, 100, 30);
        board.message.setBounds(0, 404, 324, 30);

    }

}
