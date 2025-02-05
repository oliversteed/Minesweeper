import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.Border;

public class Minesweeper {

    //Class for Minesweeper tile inherits from JButton. Private because nothing else is gonna need it... I think.
    private class MinesweeperTile extends JButton {
        int rows;
        int columns;
        boolean isMine;
        boolean isMarked;
        boolean isRevealed;

        MinesweeperTile(int rows, int columns) {
            this.rows = rows;
            this.columns = columns;

            //Adds functionality to check for mouse clicks once the object is constructed.
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {

                    //Don't allow interaction with a tile once it has been clicked and revealed.
                    if(isRevealed) return;

                    //If right-clicked, mark the tile instead of revealing and disabling.
                    if(e.getButton() == MouseEvent.BUTTON3){
                        //Unmark if already marked.
                        if(isMarked){
                            setText("");
                            isMarked = false;
                            return;
                        }
                        setText("-");
                        isMarked = true;
                        return;
                    }

                    //Disabled button once clicked. Set mines to X and safe tiles to O. This is temporary. TODO: Tiles should display number of adjacent mines, not "O"
                    if(isMine()){
                        revealTile("X");
                        revealBoard();
                    }
                    else{
                        revealTile("O");
                    }
                }
            });
        }

        //Makes the tile no longer clickable and sets text to whatever is passed as argument.
        public void revealTile(String text){
            setText(text);
            isRevealed = true;
            setEnabled(false);
        }

        public void setMine(){
            isMine = true;
        }

        public boolean isMine(){
            return isMine;
        }
    }

    //Class parameters. TODO: Create an options window on start to allow users to specify these, then allow them to launch the game with the specified parameters from this initial options/menu window
    int tileSize = 80;
    int numberOfRows = 10;
    int numberOfColumns = numberOfRows;
    int windowWidth = numberOfColumns * tileSize;
    int windowHeight = numberOfRows * tileSize;
    int totalMines = 20; //We will start with 20 for now. TODO: This should eventually be user defined and passed into the constructor.

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
                MinesweeperTile tile = new MinesweeperTile(row, col);
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

    //This function will be used to reveal all tiles upon a game over.
    //TODO: This entire function :>
    public void revealBoard(){
        System.out.println("You lose! But the functionality to end the game hasn't been implemented yet...");
    }
}
