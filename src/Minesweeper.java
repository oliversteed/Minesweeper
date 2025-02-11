import java.awt.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;

public class Minesweeper {

    //Class parameters. TODO: Create an options window on start to allow users to specify some of these, then allow them to launch the game with the specified parameters from this initial options/menu window
    int tileSize = 40;
    int numberOfRows = 15;
    int numberOfColumns = numberOfRows;
    int windowWidth = numberOfColumns * tileSize;
    int windowHeight = numberOfRows * tileSize;
    int totalMines = 40; //We will start with 40 for now.

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
                if(tile.isMine()) tile.setTileText("X");
            }
        }
    }

    public boolean isGameOver(){
        return gameOverStatus;
    }

    //Iterates across all adjacent tiles to the passed tile.
    //Cascade determines whether to spread revealed tiles or not.
    public void scanAdjacent(MinesweeperTile tile){

        //Stores revealed tiles
        ArrayList<MinesweeperTile> revealedTiles = new ArrayList<>();

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
                if(!(r == row && c == col)){

                    //sets the current adjacent tile to currentScan for easier access.
                    MinesweeperTile currentScan = playingBoardArray[r][c];

                    //If a surrounding mine is found, halt the cascading process to begin counting how many mines are adjacent.
                    if(currentScan.isMine()) {
                        tile.setAdjacentMine();
                        tile.increaseSurroundingMines();
                        tile.setTileText(Integer.toString(tile.getSurroundingMines()));
                        //return;
                    }
                    //If the evaluated tile is not a mine, reveal it and add it to the array of revealed tiles.
                    else{
                        //Stores scanned tiles ready to reveal as long as there are no adjacent mines.
                        revealedTiles.add(currentScan);
                    }
                }
            }
        }

        if(tile.isAdjacentMine()) return;

        //If no adjacent mines to the passed tile, reveal all adjacent and recur through each.
        for(MinesweeperTile scannedTile : revealedTiles){

            if(scannedTile.getRevealed()) continue; //Skip tiles that have already been passed through the function..

            scannedTile.revealTile();
            scannedTile.scanTile();
        }
    }
}
