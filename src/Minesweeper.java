import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Minesweeper {

    //Class parameters. TODO: Create an options window on start to allow users to specify some of these, then allow them to launch the game with the specified parameters from this initial options/menu window
    int tileSize = 50;
    int numberOfRows = 20;
    int numberOfColumns = numberOfRows;
    int windowWidth = numberOfColumns * tileSize;
    int windowHeight = numberOfRows * tileSize;
    int totalMines = 40; //We will start with 40 for now.

    boolean gameOverStatus = false;
    boolean adjacentMine = false;

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
                tile.revealTile(false);
            }
        }
    }

    public boolean isGameOver(){
        return gameOverStatus;
    }

    //Iterates across all adjacent tiles to the passed tile.
    //Cascade determines whether to spread revealed tiles or not.
    public void scanAdjacent(MinesweeperTile tile, boolean cascade){

        //Gets the coordinates of the passed tile in the 2D array.
        int row = tile.getRow();
        int col = tile.getColumn();

        //This iterates through every tile adjacent to the passed tile including itself.
        for(int r = row - 1; r <= row + 1; r++){

            //Skip iterations with out of bounds indices.
            if(r < 0 || r >= numberOfRows) continue;

            for(int c = col - 1; c <= col + 1; c++){

                //Skip iterations with out of bounds indices
                if(c < 0 || c >= numberOfColumns) continue;

                //Prevents the algorithm from passing the original tile
                if(!(r == row && c == col)) scanTile(playingBoardArray[r][c], tile, cascade);

            }
        }
    }

    /*
    This will take 2 tile objects and do the following based on an evaluation of objects parameters.
    If the passed tile is a mine, do not reveal.
    If the passed tile is not a mine, reveal and set the original tile as having an adjacent mine.
     */
    private void scanTile(MinesweeperTile tile, MinesweeperTile originalTile, boolean cascade){

        if(tile.isMine()) {

            //If tile was passed has already been marked for surrounding mines, begin counting up surrounding mines and return.
            if(originalTile.isAdjacentMine()) {
                originalTile.increaseSurroundingMines();
                //Set tile text to number of surrounding mines.
                originalTile.setTileText(Integer.toString(originalTile.getSurroundingMines()));
                return;
            }

            originalTile.setAdjacentMine();
            //if adjacent mine is detected, calls scanAdjacent without cascading to find number of surrounding mines.
            scanAdjacent(originalTile, false);
            return; //Do nothing and return if tile is a mine.
        }

        if(tile.getRevealed()) return; //Do nothing if tile has already been revealed.

        //revealTile() makes a call to iterateAdjacent which should recursively iterate through the board until blocked by mines.
        tile.revealTile(true);

    }

}
