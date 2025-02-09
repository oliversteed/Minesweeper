import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class Minesweeper {

    //Class parameters. TODO: Create an options window on start to allow users to specify these, then allow them to launch the game with the specified parameters from this initial options/menu window
    int tileSize = 80;
    int numberOfRows = 10;
    int numberOfColumns = numberOfRows;
    int windowWidth = numberOfColumns * tileSize;
    int windowHeight = numberOfRows * tileSize;
    int totalMines = 20; //We will start with 20 for now. TODO: This should eventually be user defined and passed into the constructor.

    boolean gameOverStatus = false;

    //Initialise objects for constructing the window.
    JFrame frame = new JFrame("Minesweeper");
    JLabel titleText = new JLabel();
    JPanel titlePanel = new JPanel();
    JPanel playingBoard = new JPanel();

    //Stores the tiles in a 2D array
    MinesweeperTile[][] playingBoardArray = new MinesweeperTile[numberOfRows][numberOfColumns];

    Minesweeper(){
        //Set window parameters
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Set title text parameters
        titleText.setFont(new Font("Arial", Font.ITALIC, 30));
        titleText.setHorizontalAlignment(JLabel.CENTER);
        titleText.setText("MINESWEEPER");
        titleText.setOpaque(true);

        //Set window layout
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleText);
        frame.add(titlePanel, BorderLayout.NORTH);

        //Set layout of playing board and add to the frame
        playingBoard.setLayout(new GridLayout(numberOfRows, numberOfColumns));
        frame.add(playingBoard);

        //Instantiate the Random object for randomly generating mines.
        Random rand = new Random();
        int generatedMines = 0;

        //Iterate through the grid and add the MinesweeperTile objects.
        for (int row = 0; row < numberOfRows; row++){
            for (int col = 0; col < numberOfColumns; col++){
                MinesweeperTile tile = new MinesweeperTile(row, col, this);
                playingBoardArray[row][col] = tile;

                tile.setFocusable(false);
                tile.setMargin(new Insets(0, 0, 0, 0));
                tile.setFont(new Font("Arial", Font.PLAIN, 45));

                /*
                 * Random chance for tile to be a mine. This is not ideal currently...
                 * This method has a chance to not generated the total number of mines.
                 * On one test run there was only 1 mine (very rare), which would be an instant win... Not ideal
                 * TODO: Work out a better method of randomly generating mines.
                 */
                if((rand.nextInt(100)+1) <= 10 && generatedMines < totalMines) {
                    tile.setMine();
                    generatedMines++;
                }

                playingBoard.add(tile);
            }
        }

        frame.setVisible(true);
    }

    //Iterates through each tile and calls its revealTile() method to reveal the entire gameboard when a gameover is triggered.
    public void gameOver(){

        if(gameOverStatus) return; //Prevents MinesweeperTile objects from recursively iterating through the entire function when a game over occurs.

        gameOverStatus = true;

        for(MinesweeperTile[] tileArr : playingBoardArray){
            for(MinesweeperTile tile : tileArr){
                tile.revealTile();
            }
        }
    }

    public boolean isGameOver(){
        return gameOverStatus;
    }

}
